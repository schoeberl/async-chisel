package async

import chisel3._
import chisel3.util._

class Delay(n: Int = 10) extends HasBlackBoxInline {
  val io = IO(new Bundle() {
    val in = Input(Bool())
    val out = Output(Bool())
  })
  setInline("Delay.v",
    s"""
       |module Delay(
       |  input in,
       |  output out
       |);
       |
       |  wire w1 /* synthesis keep */;
       |  wire w2 /* synthesis keep */;
       |  assign w1 = ~in;
       |  assign w2 = ~w1;
       |  assign #$n out = w2;
       |endmodule
       |""".stripMargin)
}
