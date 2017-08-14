package fr.hardis.baz.demo.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import fr.hardis.baz.demo.dto.Reference;

public class ReferenceFieldSetMapper implements FieldSetMapper<Reference> {

	@Override
	public Reference mapFieldSet(FieldSet fieldSet) throws BindException {

		Reference reference = new Reference();
		reference.setNumReference(fieldSet.readString(0));
		reference.setColor(fieldSet.readString(1));
		reference.setPrice(fieldSet.readDouble(2));
		reference.setSize(fieldSet.readInt(3));
		return reference;
	}
	

}
