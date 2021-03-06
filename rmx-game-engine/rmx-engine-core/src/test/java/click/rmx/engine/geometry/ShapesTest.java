/**
 * 
 */
package click.rmx.engine.geometry;

import click.rmx.debug.Tests;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import static org.junit.Assert.*;

/**
 * @author Max
 *
 */
public class ShapesTest {

	Shape shape;
	ShortBuffer byteToShortBuffer;
	ShortBuffer newShortBuffer;
	ByteBuffer byteBuffer;
	@BeforeClass
	public static void beforeClass() {
		Tests.setColWidth(ShapesTest.class);
	}
	
	@Before
	public void setUp() {
		shape = Shapes.Cube;
		byteToShortBuffer = shape.getIndexBuffer().asShortBuffer();
		newShortBuffer = BufferUtils.createShortBuffer(shape.indexSize());
		newShortBuffer.put(shape.indices());
		byteBuffer = shape.getIndexBuffer();
		
		assertTrue(byteBuffer == shape.getIndexBuffer());
	}
	
	@After
	public void tearDown() {
		shape = null;
		byteToShortBuffer = null;
		newShortBuffer = null;
		byteBuffer = null;
		System.out.println();
	}
	
	@Test
	public void CubeVertexCountIs4x4x6() {
		int vertexSize = 4 * 4 * 6;
		int indexSize = 6 * 6;
		Tests.note("Vertex Size should be " + vertexSize);
		Tests.note("Index Size should be " + indexSize);
		assertTrue(shape.vertexSize() == vertexSize);
		assertTrue(shape.indexSize() == indexSize);
		Tests.success();
	}

	@Test
	public void indexVsByteBufferLength() {
		int bbCap = byteBuffer.capacity();
		int bToSbCap = byteToShortBuffer.capacity();
		int newSbCap = newShortBuffer.capacity();
		int indexLen = shape.indices().length;
		Tests.note("ByteBuff Capacity: " + bbCap);
		Tests.note("ByteToShortBuff Capacity: " + bToSbCap);
		Tests.note("NewShortBuff Capacity: " + newSbCap);
		Tests.note("Index Lenght: " + indexLen);
		assertTrue(bbCap == indexLen * 2);
		assertTrue(bToSbCap == indexLen);
		assertTrue(newSbCap == indexLen);
	}
	
	@Test
	public void indexVsByteBufferValues() {	
		for (int i = 0; i < shape.indexSize(); ++i) {
			assertTrue(
					byteToShortBuffer.get(i) == newShortBuffer.get(i)
					&& newShortBuffer.get(i) == byteBuffer.get(i * 2) 
					);
			Tests.note(""
					+ "byteToShort: " 
					+ byteToShortBuffer.get(i)//byteToShortArr[i]
					+ ", short: " 
					+ newShortBuffer.get(i)//newShortArr[i]
					+ ", Byte: "
					+ byteBuffer.get(i * 2) //+ "/" + byteBuffer.get(i * 2 + 1)
					);
		}
		
//		assertTrue(newShortBuffer.compareTo(byteToShortBuffer) == 0);
	}
}
