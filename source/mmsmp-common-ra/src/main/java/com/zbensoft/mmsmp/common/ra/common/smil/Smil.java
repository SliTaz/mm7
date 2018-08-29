package com.zbensoft.mmsmp.common.ra.common.smil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class Smil {
	private static final Logger log = Logger.getLogger(Smil.class);

	private List<Meta> metas = new ArrayList();
	private Layout layout = null;
	private List<Par> parList = new ArrayList();

	public Smil() {
	}

	public Smil(List<Meta> metas, Layout layout, List<Par> parList) {
		this.metas = metas;
		this.layout = layout;
		this.parList = parList;
	}

	public String encode() {
		System.out.println("enter encode of smil....");
		StringBuffer result = new StringBuffer(200);
		result.append("<smil xmlns=\"http://www.w3.org/2000/SMIL20/CR/Language\">\r\n");

		if (((this.metas != null) && (this.metas.size() > 0)) || (this.layout != null)) {
			result.append("\t<head>\r\n");
			if (this.metas != null) {
				for (int i = 0; i < this.metas.size(); i++) {
					result.append("\t\t<meta name=\"" + ((Meta) this.metas.get(i)).getName() + "\" content=\"" + ((Meta) this.metas.get(i)).getContent() + "\"/>\r\n");
				}
			}
			if (this.layout != null) {
				result.append("\t\t<layout>\r\n");
				RootLayout rootLayout = this.layout.getRootLayout();
				if (rootLayout != null) {
					result.append("\t\t\t<root-layout ");
					if (rootLayout.getWidth() != null) {
						result.append("width=\"" + rootLayout.getWidth() + "\" ");
					}
					if (rootLayout.getHeight() != null) {
						result.append("height=\"" + rootLayout.getHeight() + "\"");
					}
					result.append("/>\r\n");
				} else {
					result.append("\t\t<root-layout/>");
				}
				Region region = this.layout.getImageRegion();
				if (region != null) {
					result.append("\t\t\t<region ");
					if (region.getId() != null) {
						result.append("id=\"" + region.getId() + "\" ");
					}
					if (region.getTop() != null) {
						result.append("top=\"" + region.getTop() + "\" ");
					}
					if (region.getLeft() != null) {
						result.append("left=\"" + region.getLeft() + "\" ");
					}
					if (region.getHeight() != null) {
						result.append("height=\"" + region.getHeight() + "\" ");
					}
					if (region.getWidth() != null) {
						result.append("width=\"" + region.getWidth() + "\" ");
					}
					if (region.getFit() != null)
						result.append("fit=\"" + region.getFit() + "\"");
					else {
						result.append("fit=\"hidden\"");
					}
					result.append("/>\r\n");
				}
				region = this.layout.getTextRegion();
				if (region != null) {
					result.append("\t\t\t<region ");
					if (region.getId() != null) {
						result.append("id=\"" + region.getId() + "\" ");
					}
					if (region.getTop() != null) {
						result.append("top=\"" + region.getTop() + "\" ");
					}
					if (region.getLeft() != null) {
						result.append("left=\"" + region.getLeft() + "\" ");
					}
					if (region.getHeight() != null) {
						result.append("height=\"" + region.getHeight() + "\" ");
					}
					if (region.getWidth() != null) {
						result.append("width=\"" + region.getWidth() + "\" ");
					}
					if (region.getFit() != null)
						result.append("fit=\"" + region.getFit() + "\"");
					else {
						result.append("fit=\"hidden\"");
					}
					result.append("/>\r\n");
				}
				result.append("\t\t</layout>\r\n");
			}
			result.append("\t</head>\r\n");
		}

		result.append("\t<body>\r\n");
		Par par = null;

		for (int i = 0; i < this.parList.size(); i++) {
			par = (Par) this.parList.get(i);

			result.append("\t\t<par ");

			if (par.getDur() != null) {
				result.append("dur=\"" + par.getDur() + "\"");
			}
			result.append(">\r\n");

			TreeMap tMap = new TreeMap();
			StringBuffer textStrBuffer = new StringBuffer(50);
			StringBuffer imageStrBuffer = new StringBuffer(50);
			StringBuffer audioStrBuffer = new StringBuffer(50);
			StringBuffer videoStrBuffer = new StringBuffer(50);

			if (par.getImage() != null) {
				imageStrBuffer.append("\t\t\t<img src=\"" + par.getImage().getSrc() + "\" ");

				if (par.getImage().getRegion() != null) {
					imageStrBuffer.append("region=\"" + par.getImage().getRegion() + "\"");
				}
				imageStrBuffer.append("/>\r\n");

				String indexImgKey = "";
				String imgSrcStr = par.getImage().getSrc();
				System.out.println("imgSrcStr==" + imgSrcStr);
				if ((imgSrcStr != null) && (!imgSrcStr.equals("")) && (imgSrcStr.indexOf("_") != -1)) {
					indexImgKey = imgSrcStr.substring(imgSrcStr.lastIndexOf("_") + 1, imgSrcStr.lastIndexOf("_") + 2);
				}
				String indexImgValue = "Image";
				if (!indexImgKey.equals("")) {
					tMap.put(indexImgKey, imageStrBuffer.toString());
				}
			}

			if (par.getText() != null) {
				textStrBuffer.append("\t\t\t<text src=\"" + par.getText().getSrc() + "\" ");

				if (par.getText().getRegion() != null) {
					textStrBuffer.append("region=\"" + par.getText().getRegion() + "\"");
				}
				textStrBuffer.append("/>\r\n");

				String indexTxtKey = "";
				String txtSrcStr = par.getText().getSrc();
				System.out.println("txtSrcStr==" + txtSrcStr);
				if ((txtSrcStr != null) && (!txtSrcStr.equals("")) && (txtSrcStr.indexOf("_") != -1)) {
					indexTxtKey = txtSrcStr.substring(txtSrcStr.lastIndexOf("_") + 1, txtSrcStr.lastIndexOf("_") + 2);
				}
				String indexTxtValue = "Text";
				if (!indexTxtKey.equals("")) {
					tMap.put(indexTxtKey, textStrBuffer.toString());
				}

			}

			if (par.getAutio() != null) {
				audioStrBuffer.append("\t\t\t<audio src=\"" + par.getAutio().getSrc() + "\" ");

				if (par.getAutio().getBegin() != null) {
					audioStrBuffer.append("begin=\"" + par.getAutio().getBegin() + "\" ");
				}
				if (par.getAutio().getEnd() != null) {
					audioStrBuffer.append("end=\"" + par.getAutio().getEnd() + "\" ");
				}
				if (par.getAutio().getAlt() != null) {
					audioStrBuffer.append("alt=\"" + par.getAutio().getAlt() + "\" ");
				}
				audioStrBuffer.append("/>\r\n");

				String indexAudioKey = "";
				String AudioSrcStr = par.getAutio().getSrc();
				if ((AudioSrcStr != null) && (!AudioSrcStr.equals("")) && (AudioSrcStr.indexOf("_") != -1)) {
					indexAudioKey = AudioSrcStr.substring(AudioSrcStr.lastIndexOf("_") + 1, AudioSrcStr.lastIndexOf("_") + 2);
				}
				String indexAudioValue = "Audio";
				if (!indexAudioKey.equals("")) {
					tMap.put(indexAudioKey, audioStrBuffer.toString());
				}

			}

			if (par.getVideo() != null) {
				videoStrBuffer.append("\t\t\t<video src=\"" + par.getVideo().getSrc() + "\" ");

				if (par.getVideo().getBegin() != null) {
					videoStrBuffer.append("begin=\"" + par.getVideo().getBegin() + "\" ");
				}
				if (par.getVideo().getEnd() != null) {
					videoStrBuffer.append("end=\"" + par.getVideo().getEnd() + "\" ");
				}
				if (par.getVideo().getAlt() != null) {
					videoStrBuffer.append("alt=\"" + par.getVideo().getAlt() + "\" ");
				}
				videoStrBuffer.append("/>\r\n");

				String indexVideoKey = "";
				String videoSrcStr = par.getVideo().getSrc();
				if ((videoSrcStr != null) && (!videoSrcStr.equals("")) && (videoSrcStr.indexOf("_") != -1)) {
					indexVideoKey = videoSrcStr.substring(videoSrcStr.lastIndexOf("_") + 1, videoSrcStr.lastIndexOf("_") + 2);
				}
				String indexVideoValue = "Video";
				if (!indexVideoKey.equals("")) {
					tMap.put(indexVideoKey, videoStrBuffer.toString());
				}
			}
			if ((tMap != null) && (!tMap.isEmpty())) {
				System.out.println("add index smil...");
				Iterator it = tMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry en = (Map.Entry) it.next();

					result.append(en.getValue());
				}
			} else {
				System.out.println("nothing index smil...");
				result.append(imageStrBuffer.toString());
				result.append(textStrBuffer.toString());
				result.append(audioStrBuffer.toString());
				result.append(videoStrBuffer.toString());
			}
			result.append("\t\t</par>\r\n");
		}
		result.append("\t</body>\r\n");
		result.append("</smil>");

		return result.toString();
	}

	public void encode(OutputStream out) {
		try {
			out.write(encode().getBytes("UTF-8"));
		} catch (Exception localException) {
			try {
				out.close();
			} catch (IOException ioe) {
				log.error("", ioe);
			}
		} finally {
			try {
				out.close();
			} catch (IOException ioe) {
				log.error("", ioe);
			}
		}
	}

	public void addMeta(Meta meta) {
		this.metas.add(meta);
	}

	public void addPar(Par par) {
		this.parList.add(par);
	}

	public Layout getLayout() {
		return this.layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public List<Meta> getMetas() {
		return this.metas;
	}

	public void setMetas(List<Meta> metas) {
		this.metas = metas;
	}

	public List<Par> getParList() {
		return this.parList;
	}

	public void setParList(List<Par> parList) {
		this.parList = parList;
	}

	public static void main(String[] args) {
		Smil smil = new Smil();
		RootLayout rootLayout = new RootLayout("1", "3");
		Region image = new Region("image", "1", "2", "3", "4");
		Region text = new Region("text", "1", "2", "3", "4");

		smil.setLayout(new Layout(rootLayout, image, text));
		List metas = new ArrayList();

		metas.add(new Meta("title", "锟斤拷锟斤拷"));
		metas.add(new Meta("author", "tongsj"));

		smil.setMetas(metas);

		List parList = new ArrayList();
		Par par = new Par("10s", new Text("text1", "text"), new Image("image1", "image"), null, null);
		parList.add(par);
		par = new Par("10s", new Text("text1", "text"), new Image("image1", "image"), new Audio("audio1"), null);
		parList.add(par);

		smil.setParList(parList);

		System.out.println(smil.encode());
	}

	public String toString() {
		return encode();
	}
}
