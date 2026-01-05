package guru.springframework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;

public class VendorDTO {

    @ApiModelProperty(value = "The vendor's name")
    private String name;

    @ApiModelProperty(value = "The vendor's url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
