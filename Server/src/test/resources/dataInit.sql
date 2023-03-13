INSERT INTO Cities (cityId, name, population, mostTuristicPlace, mostReserverdHotel) 
  VALUES (124, 'Frist', 123, 'Foo', 'Bar'), 
    (125, 'Second', 325, 'Fizz', 'Buzz'),
    (126, 'Third', 1452, 'Oof', 'Rab');

INSERT INTO Tourists (touristId, name, lastName, bornDate,travelBudget, travelFrequency , idCard) 
  VALUES (125, 'Fooo', '02' ,'2012-03-12', 1120.2, 5, '1127464'), 
    (126, 'User', '01' ,'2003-02-08', 10.2, 5, '1156764'), 
    (127, 'User', '01' ,'2000-12-21', 15832.2, 5, '1564');

INSERT INTO Trips (tripId, startDate, cityId,touristId) 
  VALUES  (124, '2023-10-04', 124, 125),
    (125, '2023-10-04', 124, 125), 
    (126, '2023-10-04', 124, 126), 
    (127, '2023-10-04', 124, 127);