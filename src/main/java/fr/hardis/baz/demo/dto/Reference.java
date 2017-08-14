package fr.hardis.baz.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

import org.hibernate.validator.constraints.NotBlank;

import com.google.gson.annotations.SerializedName;

import fr.hardis.baz.demo.validation.Color;

@XmlRootElement(name="reference")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reference implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1015359659351021868L;

	@NotNull
    @Size(min = 1, max = 10, message = "Too long identifier")
	@XmlAttribute
    private String numReference;

    @NotBlank
    @Color
    @XmlAttribute
    @SerializedName("type")
    private String color;
    
    @NotNull
    //@Monnaie
    @XmlAttribute
    private Double price;

    @NotNull
    @XmlAttribute
    private Integer size;

    public String getNumReference() {
		return numReference;
	}

	public void setNumReference(String numReference) {
		this.numReference = numReference;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	
    
    @Override
	public String toString() {
		return this.numReference + ";" + this.color + ";" + this.price + ";" + this.size;
	}


}
