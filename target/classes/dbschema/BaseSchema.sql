CREATE TABLE `upload_file_info` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`file_name` varchar(255) DEFAULT NULL,
	`file_path` varchar(500) DEFAULT NULL,
	`group_name` varchar(128) DEFAULT NULL,
	`store_path` varchar(500) DEFAULT NULL,
	`is_image` varchar(1) DEFAULT NULL,
	`create_date` datetime DEFAULT NULL,
	`create_user` varchar(255) DEFAULT NULL,
	`update_date` datetime DEFAULT NULL,
	`update_user` varchar(255) DEFAULT NULL,
	`valid` int(1) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
