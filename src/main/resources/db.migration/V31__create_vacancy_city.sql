CREATE TABLE IF NOT EXISTS vacancies_cities (
    vacancy_id BIGINT REFERENCES vacancies(id),
    city_id INT REFERENCES cities(id),
    PRIMARY KEY (vacancy_id, city_id)
);

ALTER TABLE vacancies_cities
ADD CONSTRAINT fk_vacancy_id
FOREIGN KEY (vacancy_id) REFERENCES vacancies(id);

ALTER TABLE vacancies_cities
ADD CONSTRAINT fk_city_id
FOREIGN KEY (city_id) REFERENCES cities(id);