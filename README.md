halbuilder-jaxrs
================

This extension integrates the HalBuilder functionality with the JAX-RS API. It provides an easy way to marshal and unmarshal Java objects using JSON or XML HAL representations

It uses annotations similar to JAXB API to identify the properties, links and embedded elements of the Object to help consuming and producing HAL representations

### Annotating Java objects
The following code show a basic yet complete example of using those annotations:
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
}
```    
The available annotations are:

* ```@HalRootElement```  Denotes a object that can be read from or written to a HAL representation
* ```@HalProperty```  Denotes a object field that represents a HAL property
* ```@HalEmbedded```  Denotes a object field that represents a HAL embedded object
* ```@HalSelfLink```  Denotes a object method that generates the link to the resource
* ```@HalLink```  Denotes a object method that generates other links related to the resource

### Marshalling and Unmarshalling
To marshal or unmarshal objects ```HalMarshaller``` and ```HalUnmarshaller``` will be used:

#### Marhsalling
```HalMarshaller.marshal```  is used for marshalling. It takes the object, the media type and an ``` OutputStream ```  as input parameters:
```java
Resource r = new Resource( 124L, "John", "1991-03-18");
HalMarshaller.marshal( r, RepresentationFactory.HAL_JSON, System.out);
``` 
### Unmarshalling
```HalUnmarshaller.unmarshal```  is used for unmarshalling. It takes the object type an ``` InputStream ```  as input parameters:

```java
Resource r1 = new Resource( 124L, "John Doe", Date.valueOf("1991-03-18"));
        
String representation = HalContext.getNewRepresentation().withBean(r1).toString( RepresentationFactory.HAL_JSON);
                
Resource r2 = null;
InputStream is = new ByteArrayInputStream( representation.getBytes(StandardCharsets.UTF_8));
      
r2 = (Resource) HalUnmarshaller.unmarshal(is, Resource.class);
``` 

### JAX-RS integration
This libary also provides a content handler, ``` HalContentHandler ``` for JAX-RS based applications. It implements both ```MessageBodyWriter``` and ````MessageBodyReader```` interfaces. Once is registered as a Provider in the configuration the following code consumes and produces HAL representations transparently:

```java
...
@GET
@Path("/resources/{id}"
@Produces({ RepresentationFactory.HAL_JSON})
public Resource getResource( @PathParam("id") Long id) {
    Resource r = new Resource();
    /* 
        Retrieve resource from database
    */
    return r;
}

@POST
@Path("/resources/{id}"
@Consumes( {RepresentationFactory.HAL_JSON})
public void saveResource( Resource r) {
    /*
        Store resource into database
    */
}
```
