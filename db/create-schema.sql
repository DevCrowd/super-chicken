create table speakers(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    surname VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    picture TEXT,
    tee_size VARCHAR(3),
    origin VARCHAR(100)
);

create table presentations(
    id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(80) NOT NULL,
    description VARCHAR(500) NOT NULL,
    language VARCHAR(3) NOT NULL
);

create table speaker_presentation(
    proposal_id VARCHAR(50),
    speaker_id VARCHAR(50),
    presentation_id VARCHAR(50),
    PRIMARY KEY(proposal_id, speaker_id, presentation_id)
);

create table votes(
    presentation_id VARCHAR(50),
    votes INTEGER NOT NULL default 0
);

CREATE OR REPLACE FUNCTION vote(in int, in int) RETURNS integer AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM votes WHERE presentation_id = $1) THEN
        INSERT INTO votes VALUES ($1, $2);
    ELSE
       UPDATE votes SET votes = votes + $2 WHERE presentation_id = $1;
    END IF;

    RETURN null;
END;
$$ LANGUAGE plpgsql;