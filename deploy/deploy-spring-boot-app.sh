#!/usr/bin/env sh

echo "!------ Installing software ------!"
apt update
apt install -y mysql-server openjdk-1.8-jdk maven nginx

echo "!------ Configuring MySQL user ------!"
mysql -u root -proot <<EOF
CREATE USER 'my_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON * . * TO 'my_user'@'localhost';
FLUSH PRIVILEGES;
EOF

echo "!------ Building Spring Boot application ------!"
cd ..

mvn clean install

mkdir -p /opt/webapp || exit
cp lesson9-spring-rest/target/lesson9-spring-rest-0.0.1-SNAPSHOT.jar /opt/webapp/app.jar

chown spring_boot:spring_boot /opt/webapp/app.jar
chmod 500 /opt/webapp/app.jar

echo "!------ Creating a systemctl daemon descriptor ------!"
cd deploy || exit

cp spring-angular.service /etc/systemd/system/spring-angular.service

echo "!------ Running the new daemon ------!"
systemctl daemon-reload
systemctl start spring-angular

echo "!------ Configuring NGINX ------!"
cp application.nginx.conf /etc/nginx/sites-available/default
systemctl restart nginx