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
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'Tô Hoài'),(2,'Nguyễn Tất Thành'),(3,'Nguyễn Du'),(4,'Vũ Trọng Phụng'),(5,'Dương Hữu Thành');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'Xuân tóc đỏ',3,'Câu chuyện về cậu bé may mắn thời kỳ bị đô hộ','1995-08-25','2008-10-12',7,3,4),(2,'Truyện Kiều',10,'Chuyện kể về cuộ đời Thúy Kiều','1890-07-20','2005-05-23',7,1,3),(3,'Dế mèn phiêu lưu ký',5,'Cuộc phiêu lưu của dế mèn','1992-07-20','2001-10-20',7,2,1),(4,'Lập Trình Hướng Đối Tượng',15,'Hướng dẫn nhập môn OOP','2011-05-24','2012-06-12',8,2,5),(5,'Lập Trình Java',10,'Hướng dẫn nhập môn Java','2019-10-21','2019-10-21',8,2,5);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bookcategory`
--

LOCK TABLES `bookcategory` WRITE;
/*!40000 ALTER TABLE `bookcategory` DISABLE KEYS */;
INSERT INTO `bookcategory` VALUES (5,'Viễn tưởng','Dãy B'),(6,'Tiểu thuyết','Dãy D'),(7,'Văn học','Dãy E'),(8,'Khoa học','Dãy D'),(9,'Tin Học','Dãy A'),(10,'Đại Học','Dãy F');
/*!40000 ALTER TABLE `bookcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `borrowingbook`
--

LOCK TABLES `borrowingbook` WRITE;
/*!40000 ALTER TABLE `borrowingbook` DISABLE KEYS */;
INSERT INTO `borrowingbook` VALUES (1,2,2,1,2,'2022-04-24','2022-05-24',_binary '\0',NULL),(2,2,1,2,3,'2022-03-20','2022-04-20',_binary '\0',NULL),(3,2,3,1,1,'2022-03-10','2022-03-15',_binary '',NULL),(4,2,1,2,3,'2022-03-20','2022-04-20',_binary '\0',NULL),(5,2,1,2,3,'2022-03-20','2022-04-20',_binary '\0',NULL),(6,2,1,2,3,'2022-06-20','2022-07-20',_binary '\0',NULL),(7,2,3,1,1,'2022-04-10','2022-05-15',_binary '',NULL),(8,2,4,1,3,'2022-05-20','2022-06-20',_binary '\0',NULL),(9,2,5,2,2,'2022-06-20','2022-07-20',_binary '',NULL),(10,2,3,2,1,'2022-07-20','2022-08-20',_binary '\0',NULL),(11,2,4,1,2,'2022-08-20','2022-09-20',_binary '',NULL),(12,2,4,2,1,'2022-09-20','2022-10-20',_binary '\0',NULL),(13,2,5,1,4,'2022-10-20','2022-11-20',_binary '\0',NULL),(14,2,3,2,4,'2022-11-20','2022-12-20',_binary '',NULL),(15,2,2,1,1,'2022-12-20','2023-01-20',_binary '',NULL),(16,2,5,2,2,'2022-01-20','2023-02-20',_binary '',NULL),(17,2,1,2,1,'2022-02-20','2022-03-20',_binary '',NULL),(18,2,3,1,3,'2022-08-20','2022-09-20',_binary '\0',NULL),(19,2,4,1,4,'2022-04-20','2022-05-20',_binary '',NULL),(20,2,5,2,5,'2022-12-20','2023-01-20',_binary '\0',NULL);
/*!40000 ALTER TABLE `borrowingbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (5,' '),(1,'Công nghệ thông tin'),(6,'Khoa Học Máy Tính'),(7,'Kinh tế');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orderingbook`
--

LOCK TABLES `orderingbook` WRITE;
/*!40000 ALTER TABLE `orderingbook` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderingbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `publishingcompany`
--

LOCK TABLES `publishingcompany` WRITE;
/*!40000 ALTER TABLE `publishingcompany` DISABLE KEYS */;
INSERT INTO `publishingcompany` VALUES (5,'BM Linh'),(2,'Kim Đồng'),(3,'Long Phụng'),(1,'Thăng Long');
/*!40000 ALTER TABLE `publishingcompany` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `readercard`
--

LOCK TABLES `readercard` WRITE;
/*!40000 ALTER TABLE `readercard` DISABLE KEYS */;
INSERT INTO `readercard` VALUES (1,'2022-04-24','2024-04-24',0,3),(2,'2020-04-25','2022-04-25',0,4);
/*!40000 ALTER TABLE `readercard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(2,'Staff'),(3,'User');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'BML','123','Bùi Mạnh Linh',_binary '\0','1995-08-25','TP Hồ Chí Minh','0123456789',1,5,'2022-04-24'),(2,'NMH','123','Nguyễn Minh Hiếu',_binary '\0','2001-12-27','TP Hồ Chí Minh','1234567890',2,1,'2022-04-23'),(3,'LHM','123','Lươn Hoàng Nam',_binary '','2001-11-01','TP Hồ Chí Minh','2345678901',3,7,'2022-04-22'),(4,'NHN','123','Nguyễn Hoàng Nam',_binary '\0','2001-02-25','TP Hồ Chí Minh','3456789012',3,1,'2022-03-11');
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

-- Dump completed on 2022-04-24 23:00:33
