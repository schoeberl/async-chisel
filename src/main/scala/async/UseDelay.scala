package async

import chisel3._

class UseDelay extends Module {
  val io = IO(new Bundle{
    val data = Output(UInt())
    val req = Output(Bool())
  })

  val s1 = Module(new ClickElement())
  val s2 = Module(new ClickElement())
  val d = Module(new Delay)

  s1.io.in.req := true.B
  s1.io.in.data := 1.U

  d.io.in := s1.io.out.req
  s2.io.in.req := d.io.out

  s2.io.in.data := s1.io.out.data
  s1.io.out.ack := s2.io.in.ack

  io.data := s2.io.out.data
  io.req := s2.io.out.req
  s2.io.out.ack := true.B
}

object UseDelay extends App {
  emitVerilog(new UseDelay(), Array("--target-dir", "generated"))
}
