package fr.hardis.baz.demo.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement(name="report")
@XmlAccessorType(XmlAccessType.FIELD)
public class FileCsv implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5031919701592607618L;

	@NotBlank
    private String inputFile;
	
	@XmlElementWrapper(name="references")
    @XmlElements({
        @XmlElement(name="reference", type=Reference.class),
    })
	//@XmlElement
    //@XmlList
	private List<Reference> references;
	
	
	@XmlElementWrapper(name="errors")
    @XmlElements({
        @XmlElement(name="error", type=Error.class),
    })
    private List<Error> errors;

    public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public List<Reference> getReferences() {
		return references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	
}

