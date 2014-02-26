package org.ardlema.akka

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit, TestActorRef}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, WordSpecLike}

class PingPongTest
  extends TestKit(ActorSystem("testSystem"))
  with ImplicitSender
  with WordSpecLike
  with BeforeAndAfterEach
  with BeforeAndAfterAll {

  "A pong actor" must {
    "send back a PongMessage when receives a PingMessage" in {
      val actorRef = TestActorRef[Pong]
      actorRef ! PingMessage
      expectMsg(PongMessage)
    }

    "be stopped receiving a StopMessage" in {
      val pongActorToBeStopped = TestActorRef[Pong]
      pongActorToBeStopped ! StopMessage
      val pongActorRef: ActorRef = pongActorToBeStopped.watch(pongActorToBeStopped)
      assert(pongActorRef.isTerminated)
    }
  }

/*
  "A ping actor" must {
    "send back a PingMessage when receives a PongMessage" in {
      val pongActorRef = TestActorRef(new Pong)
      val pingActorRef = TestActorRef(new Ping(pongActorRef))
      pingActorRef ! StartMessage
      pongActorRef.underlyingActor.receive(PingMessage)
    }

    "be stopped receiving a StopMessage" in {
      val pongActorToBeStopped = TestActorRef[Pong]
      pongActorToBeStopped ! StopMessage
      val pongActorRef: ActorRef = pongActorToBeStopped.watch(pongActorToBeStopped)
      assert(pongActorRef.isTerminated)
    }
  }*/
}
