select * from usuario
select * from Productos



INSERT INTO usuario (idUsuario, Nombre, Telefono, Correo) VALUES
    (1, 'John Doe', 1234567890, 'johndoe@example.com'),
    (2, 'Jane Smith', 9876543210, 'janesmith@example.com'),
    (3, 'Alice Johnson', 5555555555, 'alicejohnson@example.com'),
    (4, 'Bob Williams', 9999999999, 'bobwilliams@example.com'),
    (5, 'Emily Davis', 1111111111, 'emilydavis@example.com');

-- Table `mydb`.`Usuario`
ALTER TABLE `mydb`.`Usuario` MODIFY COLUMN `Telefono` BIGINT NOT NULL;


