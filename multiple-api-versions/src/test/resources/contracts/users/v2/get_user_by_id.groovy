package contracts.users.v2

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return a v2 user by id"
    name "get_user_v2_by_id"
    request {
        method GET()
        url "/api/v2/users/1"
    }
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                id: 1,
                email: 'a@b.com'
        )
    }
}

Contract.make {
    description "should return a v2 user by id"
    name "get_user_v2_by_id"
    request {
        method GET()
        url "/api/v2/users/1"
    }
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                id: 1,
                email: 'a@b.com',
                name: 'Ada',
                status: 'ACTIVE'
        )
    }
}