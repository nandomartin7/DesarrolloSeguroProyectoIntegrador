-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: minicore
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
-- Table structure for table `departamento`
--

DROP TABLE IF EXISTS `departamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departamento` (
  `id_departamento` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_departamento`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departamento`
--

LOCK TABLES `departamento` WRITE;
/*!40000 ALTER TABLE `departamento` DISABLE KEYS */;
INSERT INTO `departamento` VALUES (1,'Recursos Humanos'),(2,'Gestión de TI'),(3,'Administracion'),(4,'Finanzas'),(5,'Marketing'),(6,'Operaciones');
/*!40000 ALTER TABLE `departamento` ENABLE KEYS */;
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
INSERT INTO `empleado` VALUES ('1234567891',NULL,NULL,NULL,'Marco Antonio',NULL,NULL),('1234567892',NULL,NULL,NULL,'Bernardo Silva',NULL,NULL),('1234567893',NULL,NULL,NULL,'Luis Martínez',NULL,NULL),('1234567894',NULL,NULL,NULL,'Ana López',NULL,NULL),('1234567895',NULL,NULL,NULL,'Carlos Gómez',NULL,NULL);
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gasto`
--

DROP TABLE IF EXISTS `gasto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gasto` (
  `id_gasto` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `id_departamento` bigint DEFAULT NULL,
  `id_empleado` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`id_gasto`),
  KEY `FKlaenao3coi3r1krrd2r6ssuvg` (`id_departamento`),
  KEY `FKds9uuyjmy9cj8jp88spjvfg8n_idx` (`id_empleado`),
  CONSTRAINT `FKaesdrrweadeadeaaedeade` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`),
  CONSTRAINT `FKlaenao3coi3r1krrd2r6ssuvg` FOREIGN KEY (`id_departamento`) REFERENCES `departamento` (`id_departamento`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gasto`
--

LOCK TABLES `gasto` WRITE;
/*!40000 ALTER TABLE `gasto` DISABLE KEYS */;
INSERT INTO `gasto` VALUES (1,'pago de seguros medicos','2025-01-10',30.5,1,'1234567892'),(2,'pago de seguros medicos','2025-01-01',870,2,'1234567891'),(3,'pago de seguros medicos','2025-01-05',11,1,'1234567891'),(4,'Compra de suministros','2024-03-15',150.75,1,'1234567891'),(5,'Capacitación del personal','2024-06-10',250,1,'1234567892'),(6,'Organización de evento','2024-09-25',500,1,'1234567893'),(7,'Adquisición de software','2024-02-20',1200,2,'1234567894'),(8,'Renovación de licencias','2024-07-30',800.5,2,'1234567894'),(9,'Compra de equipos','2025-01-10',3000,2,'1234567891'),(10,'Pago de servicios públicos','2024-04-05',450,3,'1234567892'),(11,'Contratación de consultoría','2024-11-15',2000,3,'1234567893'),(12,'Compra de mobiliario','2025-01-15',750,3,'1234567894'),(13,'Auditoría interna','2024-05-10',1200,4,'1234567894'),(14,'Contratación de analistas','2024-08-20',1800,4,'1234567891'),(15,'Actualización de sistemas','2024-12-05',900,4,'1234567892'),(16,'Campaña publicitaria','2024-03-01',5000,5,'1234567893'),(17,'Diseño de logo','2024-06-25',700,5,'1234567894'),(18,'Rediseño de sitio web','2025-01-18',3500,5,'1234567894'),(19,'Mantenimiento de equipos','2024-01-15',1100,6,'1234567891'),(20,'Reparación de maquinaria','2024-10-10',3000,6,'1234567892'),(21,'Contratación de proveedores','2024-12-20',1500,6,'1234567893');
/*!40000 ALTER TABLE `gasto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-08  1:03:14
