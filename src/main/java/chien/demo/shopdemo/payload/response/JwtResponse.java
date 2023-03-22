package chien.demo.shopdemo.payload.response;

import java.util.List;

/** The type Jwt response. */
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private int id;
  private String username;
  private List<String> roles;

  /**
   * Instantiates a new Jwt response.
   *
   * @param accessToken the access token
   * @param id the id
   * @param username the username
   * @param roles the roles
   */
  public JwtResponse(String accessToken, int id, String username, List<String> roles) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }
}
