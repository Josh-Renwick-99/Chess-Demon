CREATE DATABASE  IF NOT EXISTS `cdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cdb`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cdb
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `games` (
  `gameId` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `san` varchar(1000) DEFAULT NULL,
  `winner` varchar(5) DEFAULT NULL,
  `lastMove` varchar(10) DEFAULT NULL,
  `currentFenPosition` varchar(150) NOT NULL,
  `threadName` varchar(60) NOT NULL,
  PRIMARY KEY (`gameId`),
  KEY `userId_fk_idx` (`userId`),
  CONSTRAINT `userId_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
INSERT INTO `games` VALUES (1,1,'1. e4 {[%clk 1:59:57]} c5 {[%clk 1:59:56]} 2. Nf3 {[%clk 1:59:45]} d6 {[%clk 1:59:51]} 3. d4 {[%clk 1:59:39]} cxd4 {[%clk 1:59:45]} 4. Nxd4 {[%clk 1:59:34]} Nf6 {[%clk 1:59:39]} 5. Nc3 {[%clk 1:59:26]} a6 {[%clk 1:59:35]} 6. Bd3 {[%clk 1:59:16]} g6 {[%clk 1:54:35]} 7. f3 {[%clk 1:58:42]} Bg7 {[%clk 1:29:57]} 8. Be3 {[%clk 1:57:45]} Nc6 {[%clk 1:29:16]} 9. Qd2 {[%clk 1:55:46]} Nxd4 {[%clk 1:19:30]} 10. Bxd4 {[%clk 1:55:22]} Be6 {[%clk 1:19:22]}','Black',NULL,'', null),(2,1,'test','',NULL,'', null),(3,1,'test','',NULL,'', null),(4,3,'test','',NULL,'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1', null),(5,3,'newnew','',NULL,'rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1', null),(6,3,NULL,NULL,NULL,'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1', null),(85,15,NULL,NULL,NULL,'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1', null),(103,15,'e4 e5 f1e2',NULL,NULL,'rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPPBPPP/RNBQK1NR b KQkq - 1 2', null),(104,15,'e4 e5 f1e2 e8e7 e2b5 f5 b5xd7',NULL,NULL,'rnbq1bnr/pppBk1pp/8/4pp2/4P3/8/PPPP1PPP/RNBQK1NR b KQ - 0 4', null),(105,15,'e4 e5',NULL,NULL,'rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2', null);
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-02 12:12:19
