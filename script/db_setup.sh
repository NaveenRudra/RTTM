echo "create database rttm COLLATE 'utf8_unicode_ci';"  |mysql -uroot
echo "CREATE USER 'admin'@'localhost' IDENTIFIED BY 'password';" |mysql -uroot
echo "GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost' WITH GRANT OPTION;" |mysql -uroot
echo "CREATE USER 'admin'@'%' IDENTIFIED BY 'password';" |mysql -uroot
echo "GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%' WITH GRANT OPTION;" |mysql -uroot
echo "SHOW GRANTS FOR admin;" |mysql -uroot
echo "FLUSH PRIVILEGES;" |mysql -uroot