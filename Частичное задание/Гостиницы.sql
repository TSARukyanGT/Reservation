

--USE master					
--GO
--DROP DATABASE IF EXISTS HotelNetwork
--GO

CREATE DATABASE HotelNetwork			
GO
USE HotelNetwork
GO

--DROP TABLE IF EXISTS Hotels
--GO
CREATE TABLE Hotels (
	hotelUid uniqueidentifier ROWGUIDCOL NOT NULL
		CONSTRAINT DF_uid DEFAULT (newid()),
	hotelName varchar(30),
	hotelAdress varchar(100),
	priceForDay int,
	CONSTRAINT PK_uid PRIMARY KEY (hotelUid)
)
GO

--DROP TABLE IF EXISTS Reservations
--GO
CREATE TABLE Reservations (
	reservationUid uniqueidentifier ROWGUIDCOL NOT NULL
		CONSTRAINT DF_ruid DEFAULT (newid()),
	username varchar(100),
	hotelUid uniqueidentifier NOT NULL,
	resStatus varchar(8),
	startDate Date,
	endDate Date,
	CONSTRAINT CHK_rs
		CHECK (resStatus IN ('SUCCESS', 'CANCELED')),
	CONSTRAINT FK_huid FOREIGN KEY (hotelUid) REFERENCES Hotels (hotelUid)
												ON DELETE NO ACTION
												ON UPDATE CASCADE
)
GO

--DROP TABLE IF EXISTS Loyalty
--GO
CREATE TABLE Loyalty (
	username varchar(100),
	resCount int,
	statusLvl varchar(6),
	CONSTRAINT CHK_sl
		CHECK (statusLvl IN ('BRONZE', 'SILVER', 'GOLD'))
)
GO

INSERT INTO Hotels(hotelName, hotelAdress, priceForDay) VALUES ('Ìóðîì', 'óë. Ìîñêîâñêàÿ, ä.10', 5000)

INSERT INTO Hotels(hotelName, hotelAdress, priceForDay) VALUES ('Äâîðåö', 'óë. Ëåíèíãðàäñêàÿ, ä.6', 15000)

INSERT INTO Hotels(hotelName, hotelAdress, priceForDay) VALUES ('Ýêîíîì', 'óë. Ñîâåòñêàÿ, ä.25', 2000)


--DROP TRIGGER IF EXISTS LoyaltyCheck
--GO
CREATE TRIGGER LoyaltyCheck ON Loyalty AFTER UPDATE, INSERT AS
BEGIN
	DECLARE @name varchar(100)
	SET @name = (SELECT username FROM inserted)
	UPDATE Loyalty SET statusLvl = (CASE (SELECT resCount FROM Loyalty WHERE username = @name)/10
										WHEN 0 THEN 'BRONZE'
										WHEN 1 THEN 'SILVER'
										ELSE 'GOLD'
										END) WHERE username = @name
END
GO

--DROP TRIGGER IF EXISTS ReservationChange
--GO
CREATE TRIGGER ReservationChange ON Reservations AFTER INSERT AS
BEGIN
	DECLARE @name varchar(100)
	DECLARE @nights int
	UPDATE Reservations SET resStatus = 'SUCCESS' WHERE reservationUid = (SELECT reservationUid FROM inserted);
	SELECT @name = username FROM inserted
	IF EXISTS (SELECT * FROM Loyalty WHERE username = @name)
		UPDATE Loyalty SET resCount = (SELECT resCount FROM Loyalty WHERE username = @name)+1 WHERE username = @name;
	ELSE
		INSERT INTO Loyalty (username, resCount) VALUES (@name, 1);
END
GO

--DROP TRIGGER IF EXISTS ReservationDelete
--GO
CREATE TRIGGER ReservationDelete ON Reservations AFTER UPDATE AS
BEGIN
	DECLARE @name varchar(100)
	SELECT @name = username FROM inserted
	IF (SELECT resStatus FROM inserted) = 'CANCELED'
		UPDATE Loyalty SET resCount = (SELECT resCount FROM Loyalty WHERE username = @name)-1 WHERE username = @name
END
GO

SELECT * FROM Hotels
SELECT * FROM Reservations
SELECT * FROM Loyalty

INSERT INTO Reservations (username, hotelUid, startDate, endDate) VALUES ('Ãåâîðã', '98FA348A-FAE5-4FA8-AC43-147457A3B898', '2024-04-05', '2024-04-11')
INSERT INTO Reservations (username, hotelUid, startDate, endDate) VALUES ('Îëüãà', 'BF9C7F8E-87B8-4ECC-8F38-D9F4828AC9C8', '2024-04-10', '2024-04-14')
SELECT * FROM Hotels
SELECT * FROM Reservations
SELECT * FROM Loyalty

UPDATE Reservations SET resStatus = 'CANCELED' WHERE reservationUid = '44805FF7-4D4A-4778-8E8D-EE9F89D51BFC'
