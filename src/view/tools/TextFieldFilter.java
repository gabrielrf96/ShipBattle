package view.tools;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class TextFieldFilter extends DocumentFilter {

	private int charLimit;

	public TextFieldFilter(int charLimit) {
		if (charLimit <= 0)
			throw new IllegalArgumentException("Character limit cannot be <= 0");
		this.charLimit = charLimit;
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text,
			AttributeSet attrs) throws BadLocationException {
		int currentLength = fb.getDocument().getLength();
		int overLimit = (currentLength + text.length()) - charLimit - length;
		if (overLimit > 0)
			text = text.substring(0, text.length() - overLimit);
		if (text.length() > 0)
			super.replace(fb, offset, length, text, attrs);
	}

}
