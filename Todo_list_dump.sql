-- MySQL dump 10.13  Distrib 5.7.29, for macos10.14 (x86_64)
--
-- Host: localhost    Database: Todo_list
-- ------------------------------------------------------
-- Server version	5.7.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `DTYPE` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `leader` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_p2jd4db8821l8voctujboa9oh` (`code`),
  KEY `FKhfh2lmgimog19iebwiuyyca2k` (`leader`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES ('Person',1,'a1','太郎','9CD4DBCB116D73873900F99116C173757B988124912F3C5E4CD0563627DC55FC',NULL,NULL),('Group',2,'g1','スピッツ ','45389811897E810F1A738288397257100B1E8D9E5F452DFB54C79C2B92FB3E5E','2020-09-27 18:07:38',1),('Person',3,'a2','花子','9E045338364A06F2AE23AA194A04CF442DDEA28CCD194F6A6E5C2F72E84F5BF9',NULL,NULL),('Group',4,'g2','ヘビーメロウ','45389811897E810F1A738288397257100B1E8D9E5F452DFB54C79C2B92FB3E5E','2020-09-21 17:00:27',3),('Group',5,'g3','冷たい顔','45389811897E810F1A738288397257100B1E8D9E5F452DFB54C79C2B92FB3E5E','2020-09-27 00:49:14',1),('Person',6,'a4','三郎','A8138D834FB3F574A632B6B364BBEF455614E8A5BBFD1A5F4485ED053C544CEF',NULL,NULL),('Person',7,'a5','晴子','C76621C45D592B35E9E59837E1CDAEDB8B6D9BC8D39B6DA08546F4E84CB8A319',NULL,NULL),('Group',8,'g4','月に帰る','45389811897E810F1A738288397257100B1E8D9E5F452DFB54C79C2B92FB3E5E','2020-09-27 14:08:08',1),('Group',9,'g5','ラジオデイズ','45389811897E810F1A738288397257100B1E8D9E5F452DFB54C79C2B92FB3E5E','2020-09-27 14:10:16',1);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `belongs`
--

DROP TABLE IF EXISTS `belongs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `belongs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position` varchar(255) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `group_id` int(11) DEFAULT NULL,
  `person_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKepm9mcfxfofqfbv3ywja0t0vp` (`group_id`),
  KEY `FKall2vpsclue77puq4cotaafk4` (`person_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `belongs`
--

LOCK TABLES `belongs` WRITE;
/*!40000 ALTER TABLE `belongs` DISABLE KEYS */;
INSERT INTO `belongs` VALUES (2,NULL,'2020-09-27 16:11:35',2,3),(3,'リーダー','2020-09-21 17:00:27',4,3),(4,'書記','2020-09-27 11:34:06',5,1),(9,NULL,'2020-09-27 16:13:08',2,1),(11,'リーダー','2020-09-27 14:08:08',8,1),(12,'リーダー','2020-09-27 14:10:16',9,1);
/*!40000 ALTER TABLE `belongs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `created_at` datetime NOT NULL,
  `delete_flag` int(11) NOT NULL,
  `updated_at` datetime NOT NULL,
  `comment_person` int(11) DEFAULT NULL,
  `for_comment` int(11) DEFAULT NULL,
  `for_task` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf3gl3a0un52smiserombnnq2j` (`comment_person`),
  KEY `FK10ja77n4k70naiy9it8s42078` (`for_comment`),
  KEY `FK69teqhgnuq5avyfo9xc2ht5lw` (`for_task`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'こんにちは','2020-09-27 10:41:12',0,'2020-09-27 10:41:12',3,NULL,7),(2,'こんばんは!\r\n','2020-09-27 10:41:44',1,'2020-09-27 12:57:05',1,1,7),(5,'Phpって何','2020-09-27 12:21:49',0,'2020-09-27 12:21:49',1,NULL,7),(6,'PHPはどうなの','2020-09-27 12:22:47',0,'2020-09-27 12:22:47',1,NULL,2),(7,'Niziproject','2020-09-27 16:21:32',0,'2020-09-27 16:21:32',1,NULL,6),(8,'JYP','2020-09-27 16:21:44',0,'2020-09-27 16:21:44',1,7,6),(9,'NiziUだと思います','2020-09-27 17:13:04',0,'2020-09-27 17:13:04',1,NULL,14),(10,'恋の歌→恋のうた','2020-09-27 17:21:51',0,'2020-09-27 17:21:51',1,NULL,13);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `person_id` int(11) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoe3svytx2wmemyxreor8r9dr3` (`person_id`),
  KEY `FKcqsfchpyflh9vnlq3gw6yroow` (`task_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (1,'2020-09-27 00:52:11','2020-09-27 00:52:11',1,5),(2,'2020-09-27 09:53:10','2020-09-27 09:53:10',1,8),(6,'2020-09-27 15:24:37','2020-09-27 15:24:37',1,2),(4,'2020-09-27 13:31:06','2020-09-27 13:31:06',1,3),(5,'2020-09-27 13:32:04','2020-09-27 13:32:04',3,7),(7,'2020-09-27 15:36:16','2020-09-27 15:36:16',1,6);
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `looks`
--

DROP TABLE IF EXISTS `looks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `looks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `updated_at` datetime NOT NULL,
  `looked_task_id` int(11) DEFAULT NULL,
  `peron_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhwffhgq0rvdjbv1cnlas477u4` (`looked_task_id`),
  KEY `FKeoxaxwp4bw1f9k1e01nq55q1g` (`peron_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `looks`
--

LOCK TABLES `looks` WRITE;
/*!40000 ALTER TABLE `looks` DISABLE KEYS */;
INSERT INTO `looks` VALUES (2,'2020-09-27 12:26:52',4,1),(3,'2020-09-27 17:47:31',5,1),(4,'2020-09-27 10:40:24',5,3);
/*!40000 ALTER TABLE `looks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shows`
--

DROP TABLE IF EXISTS `shows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shows` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp0vml082ak0x2g35qgrc4o4rf` (`group_id`),
  KEY `FKkqpseylqt7e3p7fu5sdjpwlct` (`task_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shows`
--

LOCK TABLES `shows` WRITE;
/*!40000 ALTER TABLE `shows` DISABLE KEYS */;
INSERT INTO `shows` VALUES (2,5,2),(4,5,4),(5,2,14),(6,5,7),(7,2,7),(8,5,8),(11,2,13),(13,2,17);
/*!40000 ALTER TABLE `shows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `deadline` datetime DEFAULT NULL,
  `finish_flag` int(11) DEFAULT NULL,
  `memo` longtext,
  `origin_task_id` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `task_leader_id` int(11) DEFAULT NULL,
  `update_person_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqtvxq4tkw5pr4064l1tyk9rce` (`account_id`),
  KEY `FKsvm0g9730q71x78gndgfi09aq` (`task_leader_id`),
  KEY `FK992co1kr2f1cb3lbuu33b2c7g` (`update_person_id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (2,'2020-09-26 21:05:18','2020-09-19 21:05:00',0,'',NULL,'java','2020-09-26 21:05:18',1,1,1),(4,'2020-09-27 00:18:40','2020-09-19 00:18:00',1,'',NULL,'php','2020-09-27 00:18:40',5,1,1),(5,'2020-09-27 00:52:08','2020-09-12 00:51:00',1,'',5,'NiziU','2020-09-27 00:52:08',2,1,1),(6,'2020-09-27 00:56:42','2020-09-08 00:51:00',NULL,'',5,'NiziU','2020-09-27 00:56:42',2,1,1),(7,'2020-09-27 11:48:18','2020-09-12 00:00:00',0,'',NULL,'php','2020-09-27 11:48:18',1,1,1),(8,'2020-09-27 09:52:49','2020-10-01 09:51:00',0,'',NULL,'python','2020-09-27 09:52:49',1,1,1),(9,'2020-09-27 09:52:41','2020-10-06 09:52:00',0,'',NULL,'Ruby','2020-09-27 09:52:41',1,1,1),(13,'2020-09-27 15:37:46','2020-10-02 15:37:00',0,'',NULL,'恋の歌','2020-09-27 15:37:46',3,3,3),(14,'2020-09-27 16:42:33','2020-09-12 00:00:00',NULL,'',5,'Nizi','2020-09-27 16:42:33',2,1,1),(17,'2020-09-27 17:51:49','2020-09-02 17:51:00',0,'',NULL,'武道館','2020-09-27 17:51:49',2,1,1);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-27 18:36:28
