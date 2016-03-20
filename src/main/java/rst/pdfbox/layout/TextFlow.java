package rst.pdfbox.layout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class TextFlow implements TextSequence {

	public static final float DEFAULT_LINE_SPACING = 1.3f;

	private final List<TextFragment> text = new ArrayList<TextFragment>();
	private float lineSpacing = DEFAULT_LINE_SPACING;
	private float preferredMaxWidth = -1;

	TextFlow() {
		super();
	}

	public void add(final TextSequence sequence) {
		for (TextFragment fragment : sequence) {
			add(fragment);
		}
	}

	public void add(final TextFragment fragment) {
		text.add(fragment);
	}

	@Override
	public Iterator<TextFragment> iterator() {
		return text.iterator();
	}

	public float getPreferredMaxWidth() {
		return preferredMaxWidth;
	}

	public void setPreferredMaxWidth(float maxWidth) {
		this.preferredMaxWidth = maxWidth;
	}

	public float getLineSpacing() {
		return lineSpacing;
	}

	public void setLineSpacing(float lineSpacing) {
		this.lineSpacing = lineSpacing;
	}

	@Override
	public float getWidth() throws IOException {
		return PdfUtil.getWidth(this, getPreferredMaxWidth());
	}

	@Override
	public float getHeight() throws IOException {
		return PdfUtil
				.getHeight(this, getPreferredMaxWidth(), getLineSpacing());
	}

	@Override
	public void drawText(PDPageContentStream contentStream,
			Coords beginOfFirstLine, Alignment alignment) throws IOException {
		PdfUtil.drawText(this, contentStream, beginOfFirstLine, alignment,
				getPreferredMaxWidth(), getLineSpacing());
	}

	public void drawTextRightAligned(PDPageContentStream contentStream,
			Coords endOfFirstLine) throws IOException {
		drawText(contentStream, endOfFirstLine.add(-getWidth(), 0),
				Alignment.Right);
	}

	@Override
	public String toString() {
		return "TextFlow [text=" + text + "]";
	}

}