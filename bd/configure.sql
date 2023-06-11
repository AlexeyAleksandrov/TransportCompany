-- Проверка таблиц
-- Маршруты
ALTER TABLE routes ADD CONSTRAINT check_route_time CHECK ( route_time > 0 );    -- время в пути
ALTER TABLE routes ADD CONSTRAINT check_route_cost_for_adult CHECK ( routes.cost_for_adult >= 0 );  -- стоимость билета для взрослого больше или равна 0
ALTER TABLE routes ADD CONSTRAINT check_route_cost_for_child CHECK ( routes.cost_for_child >= 0 );  -- стоимость билета для ребёнка больше или равна 0
ALTER TABLE routes ADD CONSTRAINT check_route_cost_for_child_to_adult CHECK ( routes.cost_for_child <= cost_for_adult );     -- стоимость билета для ребёнка меньше или равна стоимости для взрослого

-- транспорт
ALTER TABLE transports ADD CONSTRAINT check_seats_count CHECK ( seats_count > 0 );  -- кол-во посадочных мест

-- ====================================================================

-- -. Триггер для проверки того, что количество проданных на рейс билетов
-- не превышает количество мест в автобусе/микроавтобусе.

SELECT COUNT(*) AS cnt, (COUNT(*) >= t2.seats_count) AS isgt, t.schedule AS sched, route_number, schedule.date, seats_count
FROM schedule
JOIN tickets t ON schedule.id = t.schedule
JOIN transports t2 ON t2.id = schedule.transport
JOIN routes r ON r.id = schedule.route
GROUP BY t.schedule, schedule.date, route_number, t2.seats_count;

SELECT s.id, COUNT(*) >= (SELECT transports.seats_count FROM transports WHERE s.transport = transports.id) AS isgt
FROM tickets
JOIN schedule s ON s.id = tickets.schedule
JOIN transports t ON t.id = s.transport
GROUP BY s.id;

SELECT tickets.id, isgt
FROM tickets
    JOIN (
        SELECT s.id, COUNT(*) >= t.seats_count AS isgt
        FROM tickets
            JOIN schedule s ON s.id = tickets.schedule
            JOIN transports t ON t.id = s.transport
        GROUP BY s.id, t.seats_count) s ON tickets.schedule = s.id;

SELECT isgt FROM (SELECT tickets.id AS t_id, isgt
               FROM tickets
                        JOIN (
                   SELECT s.id, COUNT(*) >= t.seats_count AS isgt
                   FROM tickets
                            JOIN schedule s ON s.id = tickets.schedule
                            JOIN transports t ON t.id = s.transport
                   GROUP BY s.id, t.seats_count) s ON tickets.schedule = s.id) as tii
    WHERE t_id = 4;

-- ====================================================================

-- 1. Триггер для проверки того, что количество проданных на рейс билетов
-- не превышает количество мест в автобусе/микроавтобусе.

DROP TRIGGER check_tickets_count_trigger ON tickets;
DROP FUNCTION check_tickets_count();

CREATE OR REPLACE FUNCTION check_tickets_count()
RETURNS TRIGGER AS $$
BEGIN
    IF(SELECT isgt
               FROM (SELECT COUNT(*) AS cnt, (COUNT(*) >= t2.seats_count) AS isgt, t.schedule AS sched, route_number, schedule.date, seats_count
        FROM schedule
        JOIN tickets t ON schedule.id = t.schedule
        JOIN transports t2 ON t2.id = schedule.transport
        JOIN routes r ON r.id = schedule.route
                                GROUP BY t.schedule, schedule.date, route_number, t2.seats_count) as stt2r
        WHERE sched = new.schedule) THEN
        RAISE EXCEPTION 'На данный рейс продано максимальное кол-во билетов!';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_tickets_count_trigger
    BEFORE INSERT ON tickets
    FOR EACH ROW EXECUTE PROCEDURE check_tickets_count();

INSERT INTO tickets (schedule) VALUES (7);

-- 2. Триггер для проверки правильности формирования гос.номера транспортного средства в соответствии с установленной маской.

DROP TRIGGER check_gov_number_trigger ON transports;
DROP FUNCTION check_gov_number();

CREATE OR REPLACE FUNCTION check_gov_number()
RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.gov_number NOT SIMILAR TO '[а-я]{1}\d{3}[а-я]{2}\d{2,3}' THEN
        RAISE EXCEPTION 'Гос. номер должен соответствовать формату: WDDDWWDD или WDDDWWDDD';
    END IF;
    RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER check_gov_number_trigger
    BEFORE INSERT OR UPDATE ON transports
    FOR EACH ROW EXECUTE FUNCTION check_gov_number();

INSERT INTO transports (transport_type, gov_number, car_brand, seats_count, driver) VALUES (1, 'а543ве999', 'Мерседес-Бенц', 30, 1);

-- Триггер на маршруты -- начало и конец маршрута
-- Триггер добавление расписания на прошедшую дату
