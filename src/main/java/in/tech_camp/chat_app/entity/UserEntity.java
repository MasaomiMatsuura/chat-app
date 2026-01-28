package in.tech_camp.chat_app.entity;

import lombok.Data;

@Data
public class UserEntity {
  private Integer id;
  private String name;
  private String emai;
  private String password;
}
