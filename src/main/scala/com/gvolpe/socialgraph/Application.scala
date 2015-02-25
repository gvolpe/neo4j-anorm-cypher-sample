package com.gvolpe.socialgraph

import org.anormcypher._

object Application extends App {

  // Setup the Rest Client
  implicit val connection = Neo4jREST()

  // create some test nodes
  Cypher(
    """ CREATE (a:User { username: "gvolpe", email: "gvolpe@github.com", company: "GitHub"}),
               (b:User { username: "hcarbone", email: "hcarbone@github.com", company: "GitHub"}),
               (c:User { username: "ncavallo", email: "ncavallo@github.com", company: "GitHub"})
    """).execute()

  // a simple query
  val req: CypherStatement = Cypher(""" MATCH (u:User) WHERE u.company="GitHub" RETURN u.email """)

  // get a stream of results back
  val stream: Stream[CypherResultRow] = req()

  // get the results and put them into a list
  val list: List[Option[String]] = stream.map(row => {row[Option[String]]("u.email")}).toList
  println(list)

}
