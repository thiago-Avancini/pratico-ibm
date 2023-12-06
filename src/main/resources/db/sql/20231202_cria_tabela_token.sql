CREATE TABLE IF NOT EXISTS `token`
(
  `id` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `token_type` varchar(255) NOT NULL,
  `revogado` bit NOT NULL default 0,
  `expirado` bit NOT NULL default 0,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_TOKEN_USUARIO_ID` (`user_id`),
  CONSTRAINT `FK_TOKEN_USUARIO_ID` FOREIGN KEY (`user_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
