package org.ardlema.akka

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{TestProbe, ImplicitSender, TestKit, TestActorRef}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, WordSpecLike}



class PingPongTest
  extends TestKit(ActorSystem("testSystem"))
  with ImplicitSender
  with WordSpecLike
  with BeforeAndAfterEach
  with BeforeAndAfterAll {

  "A pong actor" must {
    "send back a PongMessage when receiving a PingMessage" in {
      val actorRef = TestActorRef[Pong]
      actorRef ! PingMessage
      expectMsg(PongMessage)
    }

    "be stopped when receiving a StopMessage" in {
      val pongActorToBeStopped = TestActorRef[Pong]
      pongActorToBeStopped ! StopMessage
      val pongActorRef: ActorRef = pongActorToBeStopped.watch(pongActorToBeStopped)
      assert(pongActorRef.isTerminated)
    }
  }


  "A ping actor" must {
    "send back a PingMessage when receiving a StartMessage" in {
      val probe1 = TestProbe()
      val pongActorRef = TestActorRef(new Pong)
      val pingActorRef = TestActorRef(new Ping(pongActorRef))
      pingActorRef ! (probe1.ref)
      pingActorRef ! StartMessage
      probe1.send(pongActorRef, PingMessage)
    }

    "send back a PingMessage when receiving a PongMessage" in {
      val probe1 = TestProbe()
      val pongActorRef = TestActorRef(new Pong)
      val pingActorRef = TestActorRef(new Ping(pongActorRef))
      pingActorRef ! (probe1.ref)
      pingActorRef ! PongMessage
      probe1.send(pongActorRef, PingMessage)
    }

    "send a StopMessage when the count reaches the upper limit" in {
      val probe1 = TestProbe()
      val pongActorRef = TestActorRef(new Pong)
      val pingActorRef = TestActorRef(new Ping(pongActorRef))
      pingActorRef ! (probe1.ref)
      for (i<-1 to 4)
        pingActorRef ! PongMessage
      probe1.send(pongActorRef, StopMessage)
      assert(pongActorRef.isTerminated)
      assert(pingActorRef.isTerminated)
    }
  }

}
