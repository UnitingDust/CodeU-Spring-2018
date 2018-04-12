package codeu.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import codeu.model.data.Profile;
import codeu.model.data.User;
import codeu.model.store.basic.ProfileStore;
import codeu.model.store.basic.UserStore;
import org.mockito.ArgumentCaptor;


public class RegisterServletTest {

 private RegisterServlet registerServlet;
 private HttpServletRequest mockRequest;
 private HttpServletResponse mockResponse;
 private RequestDispatcher mockRequestDispatcher;

 @Before
 public void setup() {
   registerServlet = new RegisterServlet();
   mockRequest = Mockito.mock(HttpServletRequest.class);
   mockResponse = Mockito.mock(HttpServletResponse.class);
   mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
   Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/register.jsp"))
       .thenReturn(mockRequestDispatcher);
 }

 @Test
 public void testDoGet() throws IOException, ServletException {
   registerServlet.doGet(mockRequest, mockResponse);

   Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
 }


 @Test
 public void testDoPost_BadUsername() throws IOException, ServletException{
 
    Mockito.when(mockRequest.getParameter("username")).thenReturn("bad !@#$% username");

    registerServlet.doPost(mockRequest, mockResponse);
    //ensures that a bad user name is a error with a message
    Mockito.verify(mockRequest)
        .setAttribute("error", "Please enter only letters, numbers, and spaces.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
 }


 @Test 
 public void testDoPost_NewUserRegistration() throws IOException, ServletException{
     
    Mockito.when(mockRequest.getParameter("username")).thenReturn("test username");

     
    UserStore mockUserStore = Mockito.mock(UserStore.class);
    ProfileStore mockProfileStore = Mockito.mock(ProfileStore.class);
    
    registerServlet.setUserStore(mockUserStore);
    registerServlet.setProfileStore(mockProfileStore);
    
    // Return false when running isUserRegistered() method
    Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(false);
    
    HttpSession mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    registerServlet.doPost(mockRequest, mockResponse);
   
    // Store the User and Profile object that were created
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    ArgumentCaptor<Profile> profileArgumentCaptor = ArgumentCaptor.forClass(Profile.class);

    //verifies that the user added is "test username"
    Mockito.verify(mockUserStore).addUser(userArgumentCaptor.capture());
    Assert.assertEquals(userArgumentCaptor.getValue().getName(), "test username");

    //Verify that the profile added to ProfileStore contains correct ID and description
    Mockito.verify(mockProfileStore).addProfile(profileArgumentCaptor.capture());
    Assert.assertEquals(profileArgumentCaptor.getValue().getUserID(), userArgumentCaptor.getValue().getId());
    Assert.assertEquals(profileArgumentCaptor.getValue().getDescription(), "");
    
    mockSession.setAttribute("user", "test username");

    Mockito.verify(mockSession).setAttribute("user", "test username");
    //if the user is already registered, they should be forwarded to the login page
    Mockito.verify(mockResponse).sendRedirect("/login");
 }
 

 @Test
 public void testDoPost_RegisteredUser() throws IOException, ServletException{
    Mockito.when(mockRequest.getParameter("username")).thenReturn("test username");

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    //returns true if user is already registered
    Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(true);
    registerServlet.setUserStore(mockUserStore);

    HttpSession mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    registerServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockUserStore, Mockito.never()).addUser(Mockito.any(User.class));

    mockSession.setAttribute("user","test username");

    Mockito.verify(mockSession).setAttribute("user", "test username");
    mockResponse.sendRedirect("/login");

    Mockito.verify(mockResponse).sendRedirect("/login");
 }
}
