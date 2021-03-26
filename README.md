
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
#        CREATE TABLE DATABASE
```
    public void init(){
        if(connection == null)return;
        try {
            Statement statement = getConnection().createStatement();
           StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CREATE TABLE IF NOT EXISTS `users` (");
            stringBuilder.append("UUID varchar(90),");
            stringBuilder.append("PLAYER varchar(32),");
            stringBuilder.append("POINTS int(16),"); 
            stringBuilder.append("primary key (UUID));");
            statement.executeUpdate(stringBuilder.toString());

            ResultSet resultSet = statement.executeQuery("SELECT * FROM `users`");
            if(resultSet.next()){
                new User(resultSet);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
```
#         UPDATE SQL
    @Override
    public String update() { 
        StringBuilder stringBuilder = new StringBuilder(); 
        stringBuilder.append("INSERT INTO `users` VALUES ("); 
        stringBuilder.append("'" + this.getUuid() + "',");
        stringBuilder.append("'" + this.getPlayerName() + "',");
        stringBuilder.append("'" + this.getPoints() + "'");
        stringBuilder.append(") ON DUPLICATE KEY UPDATE ");
        stringBuilder.append("UUID='" + this.getUuid() + "',"); 
        stringBuilder.append("PLAYER='" + this.getPlayerName() + "',");
        stringBuilder.append("POINTS='" + this.getPoints() + "'");
        return stringBuilder.toString(); 
    }
 }
 
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
    
    
