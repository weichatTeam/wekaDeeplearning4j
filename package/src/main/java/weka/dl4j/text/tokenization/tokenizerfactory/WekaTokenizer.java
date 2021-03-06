/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *    TweetNLPTokenizer.java
 *    Copyright (C) 1999-2017 University of Waikato, Hamilton, New Zealand
 *
 */

package weka.dl4j.text.tokenization.tokenizerfactory;

import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * A DeepLearning4j's Tokenizer interface to Weka Tokenizer.
 *
 * @author Felipe Bravo-Marquez
 */
public class WekaTokenizer implements Tokenizer {

  /** The TokenPreProcess Object */
  private TokenPreProcess tokenPreProcess;

  /** The Weka Tokenizer Object */
  private weka.core.tokenizers.Tokenizer wekaTokenizer;

  /** The number of tokens */
  private int numTokens = 0;

  /**
   * initializes the Object
   *
   * @param content the String to tokenize
   * @param wekaTokenizer the WekaTokenizer Object
   */
  public WekaTokenizer(String content, weka.core.tokenizers.Tokenizer wekaTokenizer) {
    this.wekaTokenizer = wekaTokenizer;
    this.wekaTokenizer.tokenize(content);
  }

  /* (non-Javadoc)
   * @see org.deeplearning4j.text.tokenization.tokenizer.Tokenizer#countTokens()
   */
  @Override
  public int countTokens() {
    return this.numTokens;
  }

  /* (non-Javadoc)
   * @see org.deeplearning4j.text.tokenization.tokenizer.Tokenizer#getTokens()
   */
  @Override
  public List<String> getTokens() {
    List<String> tokens = new ArrayList<String>();
    while (hasMoreTokens()) {
      tokens.add(nextToken());
    }
    return tokens;
  }

  /* (non-Javadoc)
   * @see org.deeplearning4j.text.tokenization.tokenizer.Tokenizer#setTokenPreProcessor(org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess)
   */
  @Override
  public void setTokenPreProcessor(TokenPreProcess tokenPreProcessor) {
    this.tokenPreProcess = tokenPreProcessor;
  }

  /* (non-Javadoc)
   * @see org.deeplearning4j.text.tokenization.tokenizer.Tokenizer#hasMoreTokens()
   */
  @Override
  public boolean hasMoreTokens() {
    return this.wekaTokenizer.hasMoreElements();
  }

  /* (non-Javadoc)
   * @see org.deeplearning4j.text.tokenization.tokenizer.Tokenizer#nextToken()
   */
  @Override
  public String nextToken() {

    this.numTokens++;

    String base = this.wekaTokenizer.nextElement();
    if (tokenPreProcess != null) base = tokenPreProcess.preProcess(base);
    return base;
  }
}
