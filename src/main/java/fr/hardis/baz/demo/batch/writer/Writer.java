package fr.hardis.baz.demo.batch.writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.hardis.baz.demo.dto.Error;
import fr.hardis.baz.demo.dto.FileCsv;
import fr.hardis.baz.demo.dto.Reference;
import fr.hardis.baz.demo.enums.FormatEnum;

public class Writer implements ItemWriter<Reference> {

	private static final Logger LOG = LoggerFactory.getLogger(Writer.class);

	private String filePath;

	private String outFilePath;

	private String format;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOutFilePath() {
		return outFilePath;
	}

	public void setOutFilePath(String outFilePath) {
		this.outFilePath = outFilePath;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public void write(List<? extends Reference> list) throws Exception {
		LOG.info("start write...");
		List<Error> errors = new ArrayList<>();
		List<Reference> references = new ArrayList<>();
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		int index = 0;
		for (Reference item : list) {
			List<Error> listErrors = this.validateReference(item, index, validator);
			if (CollectionUtils.isNotEmpty(listErrors)) {
				errors.addAll(listErrors);
			} else {
				references.add(item);
			}
			index++;
		}
		FileCsv file = new FileCsv();
		Path path = Paths.get(filePath);
		file.setInputFile(path.getFileName().toString());
		file.setErrors(errors);
		file.setReferences(references);
		LOG.info("file = {}", file.toString());

		FormatEnum formatEnum = FormatEnum.getByCode(format);

		switch (formatEnum) {
		case XML:
			this.WriterToXml(file);
			break;
		case JSON:
			this.WriterToJson(file);
			break;
		default:
			LOG.error("Inorret conversion format.Please try the -f xml/json option.");
			break;

		}
		LOG.info("finish write...");

	}

	private void WriterToJson(FileCsv file) throws IOException {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(outFilePath);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			fileWriter.write(gson.toJson(file));
		} catch (IOException e) {
			LOG.error("Error writing", e);
			throw e;
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				LOG.error("Error closing file...", e);
				throw e;
			}
		}

	}

	private void WriterToXml(FileCsv fileCsv) throws Exception {
		try {
			FileOutputStream file = new FileOutputStream(outFilePath, true);
			JAXBContext jaxbContext = JAXBContext.newInstance(FileCsv.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty("jaxb.encoding", "UTF-8");
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(fileCsv, file);

		} catch (JAXBException | FileNotFoundException e) {
			LOG.error("Error convert to xml", e);
			throw e;
		}

	}

	private List<Error> validateReference(Reference item, Integer index, Validator validator) {
		List<Error> errors = new ArrayList<>();
		Set<ConstraintViolation<Reference>> violations;
		violations = validator.validate(item);
		if (CollectionUtils.isNotEmpty(violations)) {
			for (ConstraintViolation<Reference> constraintViolation : violations) {
				Error error = new Error();
				error.setLine(index + 1);
				error.setMessage(constraintViolation.getMessage());
				error.setBody(item.toString());
				errors.add(error);
			}
		}
		return errors;
	}

}
