-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: ingenieriaweb
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id_admin` varchar(255) NOT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('SuperAdmin','admin','$2a$10$PLVWfO4mNSEBageOU24JY.jmbEnDf6YQ/dmey3KjQeZaDwSRcvdKG');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `automovil`
--

DROP TABLE IF EXISTS `automovil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `automovil` (
  `id_automovil` varchar(255) NOT NULL,
  `anio` int DEFAULT NULL,
  `ciudad circulacion` varchar(255) DEFAULT NULL,
  `marca` varchar(255) DEFAULT NULL,
  `modelo` varchar(255) DEFAULT NULL,
  `tipo de automovil` varchar(255) DEFAULT NULL,
  `uso del automovil` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_automovil`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `automovil`
--

LOCK TABLES `automovil` WRITE;
/*!40000 ALTER TABLE `automovil` DISABLE KEYS */;
INSERT INTO `automovil` VALUES ('ABC-2406',2018,'Guayaquil','TOYOTA','HILUX','Deportivo','Familiar'),('GBB-1234',2022,'Guayaquil','Kia','Rio','Automóvil','Carga / Comercio'),('GCC-1234',2022,'Guayaquil','Chevrolet','D Max','Jeep / Caminoneta','Carga / Comercio'),('GDD-1234',2015,'Guayaquil','Chevrolet','Spark','Automóvil','Trabajo'),('GFF-1234',2022,'Guayaquil','Kia','Rio','Automóvil','Carga / Comercio'),('GFF-1235',2022,'Guayaquil','Chevrolet','D Max','Jeep / Caminoneta','Carga / Comercio'),('GFF-1236',2018,'Guayaquil','Hyundai','Santa Fe','Jeep / Caminoneta','Familiar'),('GYM-9999',2019,'Quito','KIA','RIO','Automóvil','Familiar'),('PAA-1234',2015,'Quito','Chevrolet','Spark','Automóvil','Trabajo'),('PAA-1235',2018,'Guayaquil','Hyundai','Santa Fe','Jeep / Caminoneta','Familiar'),('PBB-1234',2018,'Guayaquil','Hyundai','Tucson','Jeep / Caminoneta','Familiar'),('PBJ-0125',2020,'Guayaquil','Hyundai','Ascent','Automóvil','Carga / Comercio'),('PCC-1234',2023,'Quito','Kia','Sportage','Jeep / Caminoneta','Trabajo'),('PCQ-4011',2016,'Quito','Chevrolet','Aveo','Automóvil','Familiar'),('PDD-1234',2022,'Quito','Chevrolet','D Max','Jeep / Caminoneta','Carga / Comercio'),('PEE-1234',2018,'Quito','Hyundai','Tucson','Jeep / Caminoneta','Familiar'),('PEE-1235',2018,'Quito','Hyundai','Santa Fe','Jeep / Caminoneta','Familiar'),('PEE-1236',2023,'Quito','Kia','Sportage','Jeep / Caminoneta','Trabajo'),('PGG-1234',2015,'Quito','Chevrolet','Spark','Automóvil','Trabajo'),('PGG-1235',2018,'Quito','Hyundai','Tucson','Jeep / Caminoneta','Familiar'),('PGG-1236',2022,'Quito','Kia','Rio','Automóvil','Carga / Comercio'),('PSG-2011',2010,'Quito','Kia','Picanto','Automóvil','Familiar');
/*!40000 ALTER TABLE `automovil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_cliente` varchar(255) NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('1234567890','Campos','jcampos@hotmail.com','Granados','Jorge','$2a$10$ZQFJ6O.SYdJ.Hj3MwXrDrOZJ1K5Em1mze2.OpB6LCjG29hhcO7LVy','0987654321'),('1234567891','Camacho','ecamacho@gmail.com','Kennedy','Emily','$2a$10$P9pslmUGsUpaTetFWf2/Yu6a7mKGZRcWYUJYQFxBKhqgHiSw8ZrSW','0987654322'),('1234567893','Camacho','fernando.camacho@udla.edu.ec','Quito','Fernando','$2a$10$xP3pRJNkESRVJjyMc5R58exmKCiH37gx4gYioox6G/qWmqFsqWZmi','0984147484'),('1234567895','Alvarez','aalvarez@gmail.com','Tumbaco','Ana','$2a$10$rRa9HSoP6djvLSwf6FGOeuh7GxGymXHivdosqCzwD88zedHSbVFPq','0987654323'),('1234567897','Blanco','bblanco@hotmail.com','Recreo','Bruno','$2a$10$4G4OokP4TFSJrIidJ24EJOWxZ3uAJGHYs8Z1FLvHzeZOeTm7/QSB.','0987654324'),('1234567899','Cruz','ccruz@hotmail.ec','Pomasqui','Carla','$2a$10$lt80NrU0AcG0QurB27d4t.X8sC8TdtIHYOFJmhokCRBPxd5YHhqq6','0987654325'),('1234567901','Diaz','ddiaz@yahoo.com','El Bosque','Diego','$2a$10$oDNEKnQQwSwxJvTKw0sSzuIb0pzC58W0O9c6GjtP8m1sOnyZumJnK','0987654326'),('1234567903','Escobar','eescobar@hotmail.com','Calderon','Elena','$2a$10$ljNIt/54paCsF1FwmrTzwOU48v.WyG6ejcbuw3WAX2BRfwb7i3m1y','0987654327'),('1234567905','Flores','fflores@hotmail.com','La Mariscal','Fabio','$2a$10$t7qfTquVPdaVcZLgVOYA0exEa/wJezA7W9q/0Ybnbdo4nDoyjScdG','0987654328'),('1234567907','Gomez','ggomez@gmail.com','Puembo','Gabriel','$2a$10$s9lcPmtl6DHS8IosID1SAeKYkQU1AOhYugeOKCiW5Vcx.2V0uLXAe','0987654329'),('1234567909','Herrera','hherrera@gmail.com','Bicentenario','Hugo','$2a$10$xaX9bOgm3GNgtzNEw9KibuvbmA.jcttZKtOqLc4kYhQ697FXjaUpm','0987654329');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contrato`
--

DROP TABLE IF EXISTS `contrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contrato` (
  `id_contrato` bigint NOT NULL AUTO_INCREMENT,
  `fecha finalizacion` date DEFAULT NULL,
  `fecha inicio` date DEFAULT NULL,
  `fecha renovacion` date DEFAULT NULL,
  `motivo agregados` varchar(255) DEFAULT NULL,
  `renovar contrato` bit(1) DEFAULT NULL,
  `valores agregados` double DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `id_automovil` varchar(255) DEFAULT NULL,
  `id_cliente` varchar(255) DEFAULT NULL,
  `id_plan` int DEFAULT NULL,
  PRIMARY KEY (`id_contrato`),
  UNIQUE KEY `UKs04jqmap2300jin1rm9r06h3` (`id_automovil`),
  KEY `FK9fu3mowd8w6uvgjyynfaj1ak7` (`id_cliente`),
  KEY `FKku1ji2fqg5ghvoey0cxhchr2` (`id_plan`),
  CONSTRAINT `FK9fu3mowd8w6uvgjyynfaj1ak7` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  CONSTRAINT `FKjek6lml5n9befsqpltbptphd` FOREIGN KEY (`id_automovil`) REFERENCES `automovil` (`id_automovil`),
  CONSTRAINT `FKku1ji2fqg5ghvoey0cxhchr2` FOREIGN KEY (`id_plan`) REFERENCES `plan_seguro` (`id_plan`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contrato`
--

LOCK TABLES `contrato` WRITE;
/*!40000 ALTER TABLE `contrato` DISABLE KEYS */;
INSERT INTO `contrato` VALUES (1,'1969-12-31','1969-12-31',NULL,'Excelente cliente',_binary '\0',-1.2,350,345.8,'PCQ-4011','1234567891',1),(6,'2024-12-03','2024-11-01','2024-12-02','pendiende de analisis',_binary '',0,350,350,'PBJ-0125','1234567890',1),(7,'2025-01-02','2024-11-05','2025-01-01','sobre pasa el promedio de usos con clientes similares',_binary '',1.5,575,583.625,'ABC-2406','1234567891',2),(8,'2026-11-21','2024-11-21',NULL,'Excelente cliente',_binary '\0',-1.2,100,98.8,'PSG-2011','1234567891',5),(9,'2025-10-10','2020-10-10',NULL,'',_binary '\0',0,350,350,'PAA-1234','1234567895',1),(10,'2026-11-24','2022-11-24',NULL,'',_binary '\0',0,350,350,'PAA-1235','1234567895',1),(11,'2026-04-22','2021-04-22',NULL,'',_binary '\0',0,575,575,'PBB-1234','1234567897',2),(12,'2026-02-14','2023-02-14',NULL,'',_binary '\0',0,575,575,'GBB-1234','1234567897',2),(13,'2027-07-21','2023-07-21',NULL,'',_binary '\0',0,350,350,'PCC-1234','1234567899',1),(14,'2027-11-23','2022-11-23',NULL,'',_binary '\0',0,575,575,'GCC-1234','1234567899',2),(15,'2027-01-30','2024-01-30',NULL,'',_binary '\0',0,575,575,'PDD-1234','1234567901',2),(16,'2027-03-30','2023-03-30',NULL,'',_binary '\0',0,350,350,'GDD-1234','1234567901',1),(17,'2025-04-10','2023-04-10',NULL,'',_binary '\0',0,575,575,'PEE-1234','1234567903',2),(18,'2026-05-17','2022-05-17',NULL,'',_binary '\0',0,350,350,'PEE-1235','1234567903',1),(19,'2027-06-05','2023-06-05',NULL,'',_binary '\0',0,350,350,'PEE-1236','1234567903',1),(20,'2027-07-27','2024-07-27',NULL,'',_binary '\0',0,575,575,'GFF-1234','1234567905',2),(21,'2025-12-06','2022-12-06',NULL,'',_binary '\0',0,575,575,'GFF-1235','1234567905',2),(22,'2025-09-13','2021-09-13',NULL,'',_binary '\0',0,350,350,'GFF-1236','1234567905',1),(23,'2025-08-08','2020-08-08',NULL,'',_binary '\0',0,350,350,'PGG-1234','1234567907',1),(24,'2026-10-08','2023-10-08',NULL,'',_binary '\0',0,575,575,'PGG-1235','1234567907',2),(25,'2026-02-19','2022-02-19',NULL,'',_binary '\0',0,575,575,'PGG-1236','1234567907',2);
/*!40000 ALTER TABLE `contrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `id_empleado` varchar(255) NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_empleado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES ('1234567892','Lopez','jlopez@yahoo.com','Calderon','Mateo','$2a$10$SB83dVAIFtrj1p10QjSi3OnGwePPyRE8RpMxr3KtZq6PvNvv3oXZ6','0987654322'),('1234567894','Barrionuevo','cbarrionuevo@gmail.com','Cumbaya','Camilo','$2a$10$JsIoRPfUifPEQivaLnmoC.O5IA7s0Sy68kw2zkejl5z1Q8nLdaQ5m','0987654321');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_seguro`
--

DROP TABLE IF EXISTS `plan_seguro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_seguro` (
  `id_plan` int NOT NULL AUTO_INCREMENT,
  `auxilios mecanicos` bit(1) DEFAULT NULL,
  `mantenimiento automovil` bit(1) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `perdidas parciales` bit(1) DEFAULT NULL,
  `perdidas totales` bit(1) DEFAULT NULL,
  `valor perdidas parciales` double DEFAULT NULL,
  `valor perdidas totales` double DEFAULT NULL,
  `valor plan` double DEFAULT NULL,
  PRIMARY KEY (`id_plan`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_seguro`
--

LOCK TABLES `plan_seguro` WRITE;
/*!40000 ALTER TABLE `plan_seguro` DISABLE KEYS */;
INSERT INTO `plan_seguro` VALUES (1,_binary '\0',_binary '','Basic',_binary '',_binary '',20,150,350),(2,_binary '\0',_binary '','Premium',_binary '',_binary '',250,1750,575),(5,_binary '\0',_binary '\0','Prueba Empleado',_binary '\0',_binary '\0',0,0,100),(6,_binary '',_binary '\0','Plan Básico',_binary '',_binary '',5000,10000,1200),(7,_binary '',_binary '','Plan Premium',_binary '',_binary '',1500,10000,500),(8,_binary '',_binary '\0','Prueba del Factory',_binary '',_binary '\0',250,0,175),(9,_binary '',_binary '','Prueba Buenas Practicas',_binary '',_binary '\0',200,0,350);
/*!40000 ALTER TABLE `plan_seguro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uso_seguro`
--

DROP TABLE IF EXISTS `uso_seguro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uso_seguro` (
  `id_uso` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `estado_reclamo` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `monto aprobado` double DEFAULT NULL,
  `tipo de uso` varchar(255) DEFAULT NULL,
  `id_contrato` bigint DEFAULT NULL,
  PRIMARY KEY (`id_uso`),
  KEY `FKfy8wuq6fpftwim9ohk2naqwui` (`id_contrato`),
  CONSTRAINT `FKfy8wuq6fpftwim9ohk2naqwui` FOREIGN KEY (`id_contrato`) REFERENCES `contrato` (`id_contrato`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uso_seguro`
--

LOCK TABLES `uso_seguro` WRITE;
/*!40000 ALTER TABLE `uso_seguro` DISABLE KEYS */;
INSERT INTO `uso_seguro` VALUES (1,'Guardachoque dañado y faro trizado','Aprobado','2024-11-05',50,'Choque leve',7),(3,'Cambio de Aceite','Pendiente','2024-11-21',50,'Auxilio mecanico',7),(4,'Daño en las luces de Stop','Aprobado','2021-05-19',40,'Daño electrico',9),(5,'Puerta de Copiloto rayada','Aprobado','2021-12-27',60,'Choque leve',9),(6,'Puerta Descuadrada','Aprobado','2024-01-10',150,'Choque grave',10),(7,'Manguera de Agua Rota','Aprobado','2022-12-12',25,'Daño mecanico no grave',11),(8,'Cambio de Aceite','Aprobado','2023-02-26',45,'Mantenimiento',11),(9,'Radio Dañado','Aprobado','2023-09-15',170,'Daño electrico',11),(10,'Cajuela Dañada','Aprobado','2024-07-02',350,'Choque grave',12),(11,'Llanta Ponchada','Aprobado','2024-10-09',15,'Auxilio mecanico',12),(12,'Tablero sin luces','Aprobado','2023-11-11',45,'Daño electrico',13),(13,'Faro derecho roto','Aprobado','2022-12-27',70,'Choque leve',14),(14,'Cambio de Aceite y Filtro','Aprobado','2023-02-13',60,'Mantenimiento',14),(15,'Perdida de potencia por bujía dañada','Aprobado','2023-07-30',80,'Daño mecanico no grave',14),(16,'Retrovisor derecho roto','Aprobado','2024-01-04',55,'Choque leve',14),(17,'Falla en el control de la alarma','Aprobado','2024-06-11',95,'Daño electrico',15),(18,'Bateria del carro agotada','Aprobado','2024-07-16',69,'Auxilio mecanico',15),(19,'Abolladura en el guardachoques posterior','Aprobado','2023-08-12',85,'Choque leve',16),(20,'Daño severo en la parte frontal de la carroseria','Aprobado','2023-12-26',1000,'Choque grave',17),(21,'Espejo de Retrovisor Roto','Aprobado','2023-09-07',25,'Choque leve',19),(22,'Llave olvidada dentro del automóvil','Aprobado','2023-11-14',10,'Auxilio mecanico',19),(23,'Cambio de ruliman en llantas posteriores y delanteras','Aprobado','2024-04-11',100,'Daño mecanico no grave',19),(24,'Cambio de aceite','Aprobado','2022-12-03',35,'Mantenimiento',18),(25,'Direccional izquierda quemada','Aprobado','2024-05-28',12,'Daño electrico',17),(26,'Rotura del eje delantero izquierdo','Aprobado','2024-11-06',450,'Choque grave',20),(27,'Amortiguadores dañados','Aprobado','2023-11-19',175,'Daño mecanico no grave',21),(28,'Revision del sistema ABS','Aprobado','2022-06-24',25,'Mantenimiento',22),(29,'Fuga en el radiador','Aprobado','2023-06-18',60,'Daño mecanico no grave',23),(30,'Llanta Ponchada','Aprobado','2024-08-29',15,'Auxilio mecanico',24),(31,'Accidente con vuelco','Aprobado','2023-05-30',1300,'Choque grave',25);
/*!40000 ALTER TABLE `uso_seguro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-08  0:28:49
