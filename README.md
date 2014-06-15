halbuilder-jaxrs
================

This extension integrates the HalBuilder functionality with the JAX-RS API. It provides an easy way to marshal and unmarshal Java objects using JSON or XML HAL representations

It uses annotations similar to JAXB API to identify the properties, links and embedded elements of the Object to help consuming and producing HAL representations

### Annotating Java objects

```java
@HalRootElement
public class Resource {

    @HalProperty( name="id")
    private Long id;
    @HalProperty( name="name")
    private String name;
    @HalProperty( name="birthDate")
    private Date birthDate;
    @HalEmbedded("subresources")
    private List<SubResource> subresources;


    @HalSelfLink
    public String getSelfLink()
    {
        return "resources/" + id;
    }
    
    @HalLink( "subresources")
    public String getSubResourcesLink()
    {
        return getSelfLink() + "/subresources";
    }
    
    /* Constructors, getters and setters....*/
    ...
```    
