package myclasses.loader.container.service;

@UDFService
public class UserService {
    @UDFAutowired
    ModelService modelService;

    String s;
    int length;
    byte b ;

    public void testUserService(){
        modelService.TestModel();
    }
}
