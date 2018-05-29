-- ----------------------------
-- Table structure for upload_file_info
-- ----------------------------
DROP TABLE IF EXISTS `upload_file_info`;
CREATE TABLE `upload_file_info` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`file_name` varchar(255) DEFAULT NULL,
	`file_path` varchar(500) DEFAULT NULL,
	`file_size` varchar(255) DEFAULT NULL COMMENT 'MB',
	`file_extension` varchar(128) DEFAULT NULL,
	`group_name` varchar(128) DEFAULT NULL,
	`store_path` varchar(500) DEFAULT NULL,
	`image_type` varchar(1) DEFAULT NULL,
	`create_date` datetime DEFAULT NULL,
	`create_user` varchar(255) DEFAULT NULL,
	`update_date` datetime DEFAULT NULL,
	`update_user` varchar(255) DEFAULT NULL,
	`valid` int(1) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for upload_file_info
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`goods_name` varchar(50) DEFAULT NULL,
	`goods_count` int(11) DEFAULT NULL,
	`version` int(11) DEFAULT 0,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

