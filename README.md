
#        CONNECTION
```    
public Connection connection(){ 
        if(connection != null)return connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + table, username, password);
            Logger.getAnonymousLogger().info("Connection success");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return connection;
    }

```
#         EXAMPLE SEND TO SQL

```public static void saveUser() {
        if (connection == null) return;
        try {
            Statement statement = connection.createStatement();
            for (User user : UserManager.getGetUser().values()) {
                UserCache basicCache = user.getUserCache();
                if(!basicCache.isStauts()) continue;
                statement.executeUpdate(user.getSQL());
                basicCache.setStauts(false);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
  }
    
    
