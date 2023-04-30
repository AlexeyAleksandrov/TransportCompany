-- 1. Триггер для проверки того, что количество проданных на рейс билетов
-- не превышает количество мест в автобусе/микроавтобусе.

CREATE OR REPLACE FUNCTION check_tickets_count_trigger()
RETURNS RECORD AS
    $$
    DECLARE
        shed RECORD;
    BEGIN
        shed = (SELECT * FROM schedule
            LEFT JOIN tickets t ON schedule.id = t.schedule
            LEFT JOIN transports t2 ON t2.id = schedule.transport
            LEFT JOIN transport_types tt ON tt.id = t2.transport_type
            GROUP BY schedule.id);

        RETURN shed;

    END;
    $$
LANGUAGE plpgsql;