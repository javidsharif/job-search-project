CREATE TABLE IF NOT EXISTS vacancies_special_knowledge (
    vacancy_id BIGINT REFERENCES vacancies(id),
    special_knowledge_id INT REFERENCES special_knowledge(id),
    PRIMARY KEY (vacancy_id, special_knowledge_id)
);

ALTER TABLE vacancies_special_knowledge
ADD CONSTRAINT fk_vacancy_id
FOREIGN KEY (vacancy_id) REFERENCES vacancies(id);

ALTER TABLE vacancies_special_knowledge
ADD CONSTRAINT fk_special_knowledge_id
FOREIGN KEY (special_knowledge_id) REFERENCES special_knowledge(id);