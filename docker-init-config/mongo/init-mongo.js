db.createUser({
    user: "courses-service",
    pwd: "q1w2e3",
    roles: [
        {
            role: "readWrite",
            db: "courses-service"
        }
    ]
});