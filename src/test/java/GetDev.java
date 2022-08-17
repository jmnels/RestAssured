
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.RestAssured;
import devs.Developer;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class GetDev {

    @Test(priority = 1)
    public void getDevs() throws JsonProcessingException {
        Response response = RestAssured.get("https://tech-services-1000201953.uc.r.appspot.com/developers");
//        String devs = response.as(Developer.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Developer> devs = mapper.readValue(response.asString(), new TypeReference<List<Developer>>(){});
        for(Developer d : devs){
                System.out.println(d.getFirstName() + " " + d.getLastName());
        }

    }

    @Test(priority = 2)
    public void getById() {
        Response response = RestAssured.get("https://tech-services-1000201953.uc.r.appspot.com/developer/5eceb54939e6f0000a1aab8a");
        assertEquals(response.statusCode(),200);
        Developer developer = response.getBody().as(Developer.class);
        assertEquals(developer.getId(),"5eceb54939e6f0000a1aab8a");
        System.out.println(response.asString());
    }

    @Test(priority = 3)
    public void putDev(){
        RestAssured.baseURI ="https://tech-services-1000201953.uc.r.appspot.com/";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        Developer josh = new Developer("5eceb54939e6f0000a1aab8a", "Joshua",
                "Nelson", "TypeScript", 2008);
        request.body(josh);
        Response response = request.put("/developer");
        Developer developer = response.getBody().as(Developer.class);
        assertEquals(response.statusCode(),200);
        assertEquals(developer.getFavoriteLanguage(),"TypeScript");
        assertNotNull(developer.getId());
        System.out.println(response.getStatusLine());
    }

    @Test(priority = 4)
    public void postDev(){
        RestAssured.baseURI ="https://tech-services-1000201953.uc.r.appspot.com/";
        RequestSpecification request = RestAssured.given();
        Developer josh = new Developer("5eceb54939e6f0000a1aab8a", "Joshua",
                "Nelson", "JavaScript", 2008);
        request.header("Content-Type", "application/json");
        request.body(josh);
        Response response = request.post("/developer");
        Developer developer = response.getBody().as(Developer.class);
        assertEquals(response.statusCode(),200);
        assertEquals(developer.getFirstName(),"Joshua");
        assertNotNull(developer.getId());
        System.out.println(response.getStatusLine());
        System.out.println(developer);
    }

    @Test(priority = 5)
    public void delDev() {
        Response response = RestAssured.delete("https://tech-services-1000201953.uc.r.appspot.com/developer/5eceb54939e6f0000a1aac7a");

        assertEquals(response.statusCode(),200);
        System.out.println(response.asString());
    }


}
