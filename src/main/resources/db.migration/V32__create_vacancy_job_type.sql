CREATE TABLE IF NOT EXISTS vacancies_job_types (
    vacancy_id BIGINT REFERENCES vacancies(id),
    job_type_id INT REFERENCES job_types(id),
    PRIMARY KEY (vacancy_id, job_type_id)
);

ALTER TABLE vacancies_job_types
ADD CONSTRAINT fk_vacancy_id
FOREIGN KEY (vacancy_id) REFERENCES vacancies(id);

ALTER TABLE vacancies_job_types
ADD CONSTRAINT fk_job_type_id
FOREIGN KEY (job_type_id) REFERENCES job_types(id);
