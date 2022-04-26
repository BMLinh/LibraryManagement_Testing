CREATE DATABASE  IF NOT EXISTS `librarymanagementdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `librarymanagementdb`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: librarymanagementdb
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'Tô Hoài'),(2,'Nguyễn Tất Thành'),(3,'Nguyễn Du'),(4,'Vũ Trọng Phụng'),(5,'Dương Hữu Thành');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `amount` int NOT NULL DEFAULT '0',
  `descriptions` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `publishing_year` date DEFAULT NULL,
  `date_of_entering` date DEFAULT NULL,
  `category_id` int NOT NULL,
  `publishing_company_id` int NOT NULL,
  `author_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_book_category_idx` (`category_id`),
  KEY `fk_book_publishing_company_idx` (`publishing_company_id`),
  KEY `fk_book_author_idx` (`author_id`),
  CONSTRAINT `fk_book_author` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
  CONSTRAINT `fk_book_category` FOREIGN KEY (`category_id`) REFERENCES `bookcategory` (`id`),
  CONSTRAINT `fk_book_publishing_company` FOREIGN KEY (`publishing_company_id`) REFERENCES `publishingcompany` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'Xuân tóc đỏ',3,'Câu chuyện về cậu bé may mắn thời kỳ bị đô hộ','1995-08-25','2008-10-12',7,3,4),(2,'Truyện Kiều',18,'Chuyện kể về cuộc đời Thúy Kiều','1890-07-20','2005-05-23',7,1,3),(3,'Dế mèn phiêu lưu ký',6,'Cuộc phiêu lưu của dế mèn','1992-07-20','2001-10-20',7,2,1),(4,'Lập Trình Hướng Đối Tượng',40,'Hướng dẫn nhập môn OOP','2011-05-24','2012-06-12',8,2,5),(5,'Lập Trình Java',14,'Hướng dẫn nhập môn Java','2019-10-21','2019-10-21',8,2,5);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookcategory`
--

DROP TABLE IF EXISTS `bookcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookcategory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookcategory`
--

LOCK TABLES `bookcategory` WRITE;
/*!40000 ALTER TABLE `bookcategory` DISABLE KEYS */;
INSERT INTO `bookcategory` VALUES (6,'Kinh tế','Dãy A'),(7,'Văn học','Dãy E'),(8,'Khoa học','Dãy D'),(9,'Tin Học đại cương','Dãy B'),(10,'Đại Học','Dãy F'),(26,'Kinh Tế Khối A','Dãy A');
/*!40000 ALTER TABLE `bookcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowingbook`
--

DROP TABLE IF EXISTS `borrowingbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrowingbook` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int NOT NULL,
  `book_id` int NOT NULL,
  `reader_card_id` int NOT NULL,
  `amount` int NOT NULL DEFAULT '1',
  `created_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `active` bit(1) DEFAULT b'0',
  `fine` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_borrowingbook_user` (`staff_id`),
  KEY `fk_borrowingbook_book` (`book_id`),
  KEY `fk_borrowingbook_reader_card_idx` (`reader_card_id`),
  CONSTRAINT `fk_borrowingbook_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `fk_borrowingbook_reader_card` FOREIGN KEY (`reader_card_id`) REFERENCES `readercard` (`id`),
  CONSTRAINT `fk_borrowingbook_user` FOREIGN KEY (`staff_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowingbook`
--

LOCK TABLES `borrowingbook` WRITE;
/*!40000 ALTER TABLE `borrowingbook` DISABLE KEYS */;
INSERT INTO `borrowingbook` VALUES (1,2,2,1,2,'2022-04-24','2022-05-24',_binary '',NULL),(3,2,3,1,1,'2022-03-10','2022-03-15',_binary '\0',NULL),(4,2,1,2,3,'2022-03-20','2022-04-26',_binary '',30000),(5,2,1,2,3,'2022-03-20','2022-04-26',_binary '',0),(6,2,1,2,3,'2022-06-20','2022-04-25',_binary '\0',0),(7,2,3,1,1,'2022-04-10','2022-05-15',_binary '\0',NULL),(8,2,4,1,3,'2022-02-20','2022-04-25',_binary '',175000),(9,2,5,2,2,'2022-06-20','2022-07-20',_binary '',NULL),(10,2,3,2,1,'2022-07-20','2022-08-20',_binary '',NULL),(11,2,4,1,2,'2022-08-20','2022-09-20',_binary '',NULL),(12,2,4,2,1,'2022-09-20','2022-04-25',_binary '\0',0),(13,2,5,1,4,'2022-10-20','2022-04-25',_binary '',0),(14,2,3,2,4,'2022-11-20','2022-12-20',_binary '',NULL),(15,2,2,1,1,'2022-12-20','2023-01-20',_binary '',NULL),(16,2,5,2,2,'2022-01-20','2023-02-20',_binary '',NULL),(17,2,1,2,1,'2022-02-20','2022-03-20',_binary '',NULL),(18,2,3,1,3,'2022-08-20','2022-04-25',_binary '',0),(19,2,4,1,4,'2022-04-20','2022-05-20',_binary '',NULL),(20,2,5,2,5,'2022-12-20','2023-01-20',_binary '\0',NULL),(27,2,1,1,2,'2022-04-26','2022-04-26',_binary '',0);
/*!40000 ALTER TABLE `borrowingbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (5,' '),(1,'Công nghệ thông tin'),(6,'Khoa Học Máy Tính'),(7,'Kinh tế');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderingbook`
--

DROP TABLE IF EXISTS `orderingbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderingbook` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_id` int NOT NULL,
  `reader_card_id` int NOT NULL,
  `amount` int NOT NULL DEFAULT '1',
  `created_date` datetime NOT NULL,
  `expired_date` datetime NOT NULL,
  `active` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `fk_orderingbook_book_idx` (`book_id`),
  KEY `kf_orderingbook_reader_card_idx` (`reader_card_id`),
  CONSTRAINT `fk_orderingbook_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `kf_orderingbook_reader_card` FOREIGN KEY (`reader_card_id`) REFERENCES `orderingbook` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderingbook`
--

LOCK TABLES `orderingbook` WRITE;
/*!40000 ALTER TABLE `orderingbook` DISABLE KEYS */;
INSERT INTO `orderingbook` VALUES (1,1,1,1,'2022-04-25 02:12:55','2022-04-26 02:12:55',_binary ''),(2,1,1,2,'2022-04-25 02:14:00','2022-04-26 11:01:00',_binary ''),(3,3,1,2,'2022-04-25 02:16:05','2022-04-27 05:48:05',_binary ''),(4,3,1,1,'2022-04-25 16:59:15','2022-04-30 09:16:05',_binary ''),(6,1,1,1,'2022-04-26 16:30:17','3922-05-28 00:00:00',_binary ''),(7,2,2,2,'2022-04-26 16:43:10','3922-05-28 00:00:00',_binary ''),(8,1,1,1,'2022-04-26 16:44:28','3922-05-28 00:00:00',_binary ''),(9,2,1,2,'2022-04-26 17:25:53','3922-05-28 00:00:00',_binary ''),(10,1,1,2,'2022-04-26 21:03:33','2022-04-28 21:03:33',_binary ''),(11,1,1,4,'2022-04-26 21:08:04','2022-04-28 21:08:04',_binary '\0');
/*!40000 ALTER TABLE `orderingbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publishingcompany`
--

DROP TABLE IF EXISTS `publishingcompany`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publishingcompany` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publishingcompany`
--

LOCK TABLES `publishingcompany` WRITE;
/*!40000 ALTER TABLE `publishingcompany` DISABLE KEYS */;
INSERT INTO `publishingcompany` VALUES (2,'Kim Đồng'),(3,'Long Phụng'),(22,'test'),(1,'Thăng Long');
/*!40000 ALTER TABLE `publishingcompany` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `readercard`
--

DROP TABLE IF EXISTS `readercard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `readercard` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `amount` int unsigned DEFAULT '0',
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reader_card_user_idx` (`user_id`),
  CONSTRAINT `fk_reader_card_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `readercard`
--

LOCK TABLES `readercard` WRITE;
/*!40000 ALTER TABLE `readercard` DISABLE KEYS */;
INSERT INTO `readercard` VALUES (1,'2022-04-24','2024-04-24',2,3),(2,'2020-04-25','2022-04-25',0,4),(4,'2020-03-25','2023-05-20',0,5);
/*!40000 ALTER TABLE `readercard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(5,'MMMMMMMMM12'),(2,'Staff'),(3,'User');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fullname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` bit(1) NOT NULL DEFAULT b'0',
  `dob` date DEFAULT NULL,
  `address` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` int NOT NULL,
  `department_id` int NOT NULL,
  `created_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  KEY `fk_user_role_idx` (`role_id`),
  KEY `fk_user_departmeny_idx` (`department_id`),
  CONSTRAINT `fk_user_departmeny` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'BML','1','Bùi Mạnh Linh',_binary '\0','1995-08-25','TP Hồ Chí Minh','0123456789',1,5,'2022-04-24'),(2,'NMH','1','Nguyễn Minh Hiếu',_binary '\0','2001-12-27','TP Hồ Chí Minh','1234567890',2,1,'2022-04-23'),(3,'LHM','1','Lươn Hoàng Nam',_binary '','2001-11-01','TP Hồ Chí Minh','2345678901',3,7,'2022-04-22'),(4,'NHN','1','Nguyễn Hoàng Nam',_binary '\0','2001-02-25','TP Hồ Chí Minh','3456789012',3,1,'2022-03-11'),(5,'Test','1','Tester',_binary '\0','2001-09-19','TP Hồ Chí Minh','4567890123',3,1,'2022-04-25'),(6,'name','1','fullname',_binary '\0','2001-02-19','TP Hồ Chí Minh','5678901234',3,1,'2022-04-25'),(7,'t1','1','fullname1',_binary '\0','2001-02-19','TP Hồ Chí Minh','6789012345',3,1,'2022-04-25'),(8,'t2','1','fullname2',_binary '\0','2001-02-19','TP Hồ Chí Minh','7890123456',3,1,'2022-04-25'),(9,'t3','1','fullname3',_binary '\0','2001-02-19','TP Hồ Chí Minh','8901234567',3,1,'2022-04-25'),(10,'t4','1','fullname4',_binary '\0','2001-02-19','TP Hồ Chí Minh','9012345678',3,1,'2022-04-25'),(11,'t5','1','fullname5',_binary '\0','2001-02-19','TP Hồ Chí Minh','0012345678',3,1,'2022-04-25');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-26 21:11:05
