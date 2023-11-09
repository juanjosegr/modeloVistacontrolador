DROP TABLE PERSONAS CASCADE CONSTRAINTS;
DROP SEQUENCE PERSONAS_ID_Seq;

CREATE TABLE PERSONAS (
    DNI VARCHAR(255) PRIMARY KEY,
    FechaNacimiento DATE,
    Nombre VARCHAR(255),
    Apellidos VARCHAR(255),
    Trabajo VARCHAR(255),
    Sueldo INT
);

INSERT INTO PERSONAS (DNI, FechaNacimiento, Nombre, Apellidos, Trabajo, Sueldo)
VALUES
    ('12345678', TO_DATE('15-01-1993', 'DD-MM-YYYY'), 'Juan Jose', 'Galvin', 'Mecanico', 1000.0);

INSERT INTO PERSONAS (DNI, FechaNacimiento, Nombre, Apellidos, Trabajo, Sueldo)
VALUES
    ('12345679', TO_DATE('20-02-1993', 'DD-MM-YYYY'), 'Pedro', 'Reyes', 'Programador', 1300.0);

INSERT INTO PERSONAS (DNI, FechaNacimiento, Nombre, Apellidos, Trabajo, Sueldo)
VALUES
    ('12345680', TO_DATE('10-03-1993', 'DD-MM-YYYY'), 'Adrian', 'Aragon', 'Segurdiad', 980.0);


CREATE SEQUENCE PERSONAS_ID_Seq START WITH 12345680 INCREMENT BY 1;
commit;

-- Ejecuta la consulta
select * from PERSONAS;