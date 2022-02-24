package async

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class ClickElementTest extends AnyFlatSpec with ChiselScalatestTester {

  // This test will fail, as Treadle can probably not do this
  "ClickElement" should "work" in {
    test(new ClickElement) { dut =>
      dut.io.in.req.poke(true.B)
      println(dut.io.in.ack.peek())
    }
  }
}
