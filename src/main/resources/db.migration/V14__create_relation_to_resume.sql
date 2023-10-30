create TABLE preffered_work (
    id SERIAL PRIMARY KEY,
    area_in_which_you_work VARCHAR(50),
    job_graphic VARCHAR(50),
    work_level VARCHAR(50),
    study_level VARCHAR(50),
    preffered_salary VARCHAR(50),
    currency VARCHAR(50),
    special_knowledge VARCHAR(50),
    work_experience VARCHAR(50),
    resume_id BIGINT REFERENCES resume(id)
);

create TABLE education (
    id SERIAL PRIMARY KEY,
    education_name VARCHAR(50),
    level VARCHAR(40),
    faculty VARCHAR(50),
    date_of_graduation DATE,
    additional_information VARCHAR(1000),
    resume_id BIGINT REFERENCES resume(id)
);

create TABLE work_experience (
id SERIAL PRIMARY KEY,
company VARCHAR(50),
position VARCHAR(50),
date_of_start_job DATE,
date_of_end_job DATE,
is_continued_job BOOLEAN,
additional_information VARCHAR(1000),
resume_id BIGINT REFERENCES resume(id)
);

create TABLE knowledge (
id SERIAL PRIMARY KEY,
name VARCHAR(30),
level VARCHAR(50),
resume_id BIGINT REFERENCES resume(id)
);

create TABLE language (
id SERIAL PRIMARY KEY,
name VARCHAR(30),
level VARCHAR(50),
resume_id BIGINT REFERENCES resume(id)
);

create TABLE acievement_and_award (
id SERIAL PRIMARY KEY,
name VARCHAR(30),
year VARCHAR(4),
description VARCHAR(1000),
resume_id BIGINT REFERENCES resume(id)
);

