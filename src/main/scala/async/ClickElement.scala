package async

import chisel3._

// TODO: make it generic
// TODO: maybe use decoupled? Or better not, as this is a different handshake
class Channel extends Bundle {
  val req = Input(Bool())
  val ack = Output(Bool())
  val data = Input(UInt(8.W))
}



class ClickElement extends Module {
  val io = IO(new Bundle {
    val in = new Channel()
    val out = Flipped(new Channel())
  })

  // Connection to the handshake register in other clock domain
  val hsOut = Wire(Bool())
  io.in.ack := hsOut
  io.out.req := hsOut

  // Generate the click
  val hsClock = Wire(Bool())
  hsClock := (io.in.req ^ hsOut) & ~(hsOut ^ io.out.ack)

  // TODO: how is reset handled in asynchronous circuits?
  withClock(hsClock.asClock) {
    val regHS = Reg(Bool())
    regHS := !regHS
    hsOut := regHS

    val regData = RegNext(io.in.data)
    io.out.data := regData
  }
}

object ClickElement extends App {
  println("Generating the click element hardware")
  emitVerilog(new ClickElement(), Array("--target-dir", "generated"))
}