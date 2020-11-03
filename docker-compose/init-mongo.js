// docker-compose -f mongodb-compose.yaml up -d
// docker exec -it mongodb /bin/bash
// mongo -u root -p Pas@123 --authenticationDatabase admin
// mongo -u spring-kotlin-user -p spring-kotlin-pass --authenticationDatabase spring-kotlin-restful
// use spring-kotlin-restful
db.createUser({
    user: 'spring-kotlin-user',
    pwd: 'spring-kotlin-pass',
    roles: [
        {
            role: 'readWrite',
            db: 'spring-kotlin-restful'
        }
    ]
})


