-- MySQL dump 10.13  Distrib 5.5.27, for Win64 (x86)
--
-- Host: localhost    Database: bookstore
-- ------------------------------------------------------
-- Server version	5.5.27

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `author` varchar(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `sales` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `img_path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'解忧杂货铺','东野圭吾',39.80,100115,1001024,'/uploads/1656609141419.jpg'),(2,'百年孤独','西亚马尔克斯',50.00,63,12608,'/uploads/1656522611195.jpg'),(3,'傲慢与偏见','简·奥斯汀',26.60,3985,99800,'/uploads/1656522657956.jpg'),(6,'三体Ⅱ黑暗森林','刘慈欣',66.00,1351145,1664855,'/uploads/1656573547503.jpg'),(7,'挪威的森林','村上春树',29.00,75,2313230,'/uploads/1656609191311.jpg'),(8,'小王子','埃克苏佩里',32.00,2093022,318,'/uploads/1656609272764.jpg'),(9,'瓦尔登湖','梭罗',29.00,212,19,'/uploads/1656609227794.jpg'),(10,'云边有个小卖部','张嘉佳',30.00,26,1327,'/uploads/1656573812508.jpg'),(11,'穆斯林的葬礼','霍达',45.00,126,2320,'/uploads/1656573850207.jpg'),(12,'追风筝的人','卡勒德·胡赛尼',29.00,2667,17,'/uploads/1656573931568.jpg'),(14,'沉默的大多数','王小波',27.00,23,43,'/uploads/1656609394101.jpg'),(15,'无人生还','阿加莎 · 克里斯蒂',33.00,23,43,'/uploads/1656609432144.jpg'),(16,'人间词话','王国维',36.00,26,121,'/uploads/1656609494607.jpg'),(17,'1000极致旅行体验','孤独星球',66.00,66,23,'/uploads/1656609628932.jpg'),(18,'三体Ⅰ地球往事','刘慈欣',29.80,100103,1616156,'/uploads/1656609707275.jpg'),(19,'嫌疑人X的献身','东野圭吾',29.80,1014,6,'/uploads/1656609741704.jpg'),(20,'呐喊','鲁迅',66.00,66,15351,'/uploads/1656609767546.jpg'),(21,'围城','钱钟书',25.00,20304,12,'/uploads/1656609803231.jpg'),(22,'海底两万里','儒勒 · 凡尔纳',58.00,123516,135132,'/uploads/1656609846299.jpg'),(23,'三体Ⅲ死神永生','刘慈欣',46.00,21313,3,'/uploads/1656609879481.jpg'),(24,'流浪地球','刘慈欣',45.00,16984,151531,'/uploads/1656609915012.jpg'),(25,'球状闪电','刘慈欣',34.00,65,100,'/uploads/1656609938375.jpg'),(26,'资治通鉴','司马迁',45.00,101,20,'/uploads/1656609966941.jpg'),(27,'活着','余华',22.00,102921,197,'/uploads/1656609992766.jpg'),(28,'霍乱时期的爱情','加西亚 · 马尔克斯',55.00,123,23,'/uploads/1656610033039.jpg'),(29,'偷影子的人','马克 · 李维',29.66,1331334,123,'/uploads/1656610080188.jpg'),(30,'月亮与六便士','毛姆',29.00,201,20,'/uploads/1656610125055.jpg'),(31,'老人与海','海明威',36.00,30001,1228,'/uploads/1656610163551.jpg'),(32,'白鹿原','陈忠实',50.00,73484,321,'/uploads/1656610202586.jpg'),(33,'明朝那些事','当年明月',200.00,131213,153153,'/uploads/1656610227004.jpg'),(34,'理想国','柏拉图',34.00,13515,1,'/uploads/1656610257633.jpg'),(35,'数据库系统概率','王珊',50.00,2314515,399,'/uploads/1656610298208.jpg'),(36,'计算机网络','谢希仁',59.80,1091,31,'/uploads/1656610321060.jpg'),(37,'许三观卖血记','余华',298.00,10001,99,'/uploads/1656610378961.jpg'),(38,'平凡的世界','路遥',60.00,100015,33,'/uploads/1656610424479.jpg'),(39,'天才在左疯子在右','高铭',38.00,123151,231,'/uploads/1656610555731.jpg'),(40,'大奉打更人','卖报小郎君',30.00,123,162,'/uploads/1656610604238.jpg');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `order_id` varchar(50) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `status` int(11) NOT NULL,
  `username` varchar(12) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_username` (`username`),
  CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('1656593679409admin','2022-06-30 20:54:39',351.20,2,'admin'),('1656599326669admin','2022-06-30 22:28:46',200.40,1,'admin'),('1656599335296admin','2022-06-30 22:28:55',230.20,1,'admin'),('1656599343888admin','2022-06-30 22:29:03',200.40,0,'admin'),('1656599350698admin','2022-06-30 22:29:10',121.00,0,'admin'),('1656599361439admin','2022-06-30 22:29:21',174.00,0,'admin'),('1656599381433admin','2022-06-30 22:29:41',123.80,1,'admin'),('1656639101637admin','2022-07-01 09:31:41',182.40,0,'admin');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitem`
--

DROP TABLE IF EXISTS `orderitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `order_id` varchar(50) DEFAULT NULL,
  `img_path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_id` (`order_id`),
  CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitem`
--

LOCK TABLES `orderitem` WRITE;
/*!40000 ALTER TABLE `orderitem` DISABLE KEYS */;
INSERT INTO `orderitem` VALUES (33,'三体Ⅰ地球往事',1,29.80,29.80,'1656593679409admin','/uploads/1656522003420.jpg'),(34,'百年孤独',1,50.00,50.00,'1656593679409admin','/uploads/1656522611195.jpg'),(35,'傲慢与偏见',1,26.60,26.60,'1656593679409admin','/uploads/1656522657956.jpg'),(36,'嫌疑人X的献身',1,29.80,29.80,'1656593679409admin','/uploads/1656573666209.jpg'),(37,'呐喊',1,28.00,28.00,'1656593679409admin','/uploads/1656573610868.jpg'),(38,'三体Ⅱ黑暗森林',1,66.00,66.00,'1656593679409admin','/uploads/1656573547503.jpg'),(39,'云边有个小卖部',1,30.00,30.00,'1656593679409admin','/uploads/1656573812508.jpg'),(40,'穆斯林的葬礼',1,45.00,45.00,'1656593679409admin','/uploads/1656573850207.jpg'),(41,'三体Ⅲ死神永生',1,46.00,46.00,'1656593679409admin','/uploads/1656573725147.jpg'),(42,'三体Ⅰ地球往事',1,29.80,29.80,'1656599326669admin','/uploads/1656522003420.jpg'),(43,'百年孤独',1,50.00,50.00,'1656599326669admin','/uploads/1656522611195.jpg'),(44,'傲慢与偏见',1,26.60,26.60,'1656599326669admin','/uploads/1656522657956.jpg'),(45,'三体Ⅱ黑暗森林',1,66.00,66.00,'1656599326669admin','/uploads/1656573547503.jpg'),(46,'呐喊',1,28.00,28.00,'1656599326669admin','/uploads/1656573610868.jpg'),(47,'三体Ⅰ地球往事',1,29.80,29.80,'1656599335296admin','/uploads/1656522003420.jpg'),(48,'百年孤独',1,50.00,50.00,'1656599335296admin','/uploads/1656522611195.jpg'),(49,'傲慢与偏见',1,26.60,26.60,'1656599335296admin','/uploads/1656522657956.jpg'),(50,'三体Ⅱ黑暗森林',1,66.00,66.00,'1656599335296admin','/uploads/1656573547503.jpg'),(51,'呐喊',1,28.00,28.00,'1656599335296admin','/uploads/1656573610868.jpg'),(52,'嫌疑人X的献身',1,29.80,29.80,'1656599335296admin','/uploads/1656573666209.jpg'),(53,'三体Ⅰ地球往事',1,29.80,29.80,'1656599343888admin','/uploads/1656522003420.jpg'),(54,'百年孤独',1,50.00,50.00,'1656599343888admin','/uploads/1656522611195.jpg'),(55,'傲慢与偏见',1,26.60,26.60,'1656599343888admin','/uploads/1656522657956.jpg'),(56,'三体Ⅱ黑暗森林',1,66.00,66.00,'1656599343888admin','/uploads/1656573547503.jpg'),(57,'呐喊',1,28.00,28.00,'1656599343888admin','/uploads/1656573610868.jpg'),(58,'穆斯林的葬礼',1,45.00,45.00,'1656599350698admin','/uploads/1656573850207.jpg'),(59,'云边有个小卖部',1,30.00,30.00,'1656599350698admin','/uploads/1656573812508.jpg'),(60,'三体Ⅲ死神永生',1,46.00,46.00,'1656599350698admin','/uploads/1656573725147.jpg'),(61,'追风筝的人',6,29.00,174.00,'1656599361439admin','/uploads/1656573931568.jpg'),(67,'三体Ⅱ黑暗森林',1,66.00,66.00,'1656599381433admin','/uploads/1656573547503.jpg'),(68,'呐喊',1,28.00,28.00,'1656599381433admin','/uploads/1656573610868.jpg'),(69,'嫌疑人X的献身',1,29.80,29.80,'1656599381433admin','/uploads/1656573666209.jpg'),(78,'解忧杂货铺',1,39.80,39.80,'1656639101637admin','/uploads/1656609141419.jpg'),(79,'百年孤独',1,50.00,50.00,'1656639101637admin','/uploads/1656522611195.jpg'),(80,'傲慢与偏见',1,26.60,26.60,'1656639101637admin','/uploads/1656522657956.jpg'),(81,'三体Ⅱ黑暗森林',1,66.00,66.00,'1656639101637admin','/uploads/1656573547503.jpg');
/*!40000 ALTER TABLE `orderitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(12) NOT NULL,
  `password` varchar(12) NOT NULL,
  `email` varchar(20) DEFAULT NULL,
  `admin` bit(1) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','admin','admin@qq.com',''),('mycentos','123456','123456@qq.com','\0'),('testuser','123456','123456@qq.com','\0'),('wasd123','123456','123456@qq.com','\0');
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

-- Dump completed on 2022-07-01 10:40:04
