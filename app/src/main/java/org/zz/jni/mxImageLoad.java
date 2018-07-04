package org.zz.jni;

public class mxImageLoad {
	static {
		System.loadLibrary("mxImageLoad");
	}
	
	/*******************************************************************************************
	��	�ܣ�	ͼ���ļ����ص��ڴ�
	��	����	szLoadFilePath		- ����	ͼ��·��
				pRGBBuffer          - ���	�ⲿͼ�񻺳���,���ڽ���RGBͼ�����ݣ����ΪNULL���򲻽���
				pRawBuffer			- ���	�ⲿͼ�񻺳���,���ڽ��ջҶ�ͼ�����ݣ����ΪNULL���򲻽���
				oX					- ���	ͼ����
				oY					- ���	ͼ��߶�
	��	�أ�	1-�ɹ�������-ʧ��
*******************************************************************************************/
	public native  int LoadFaceImage(String szLoadFilePath,
			byte[] pRGBBuffer,
			byte[] pGrayBuffer,
						 int[] oX, 
						 int[] oY);

/*******************************************************************************************
	��	�ܣ�	����ͼ������
	��	����	szSaveFilePath		- ����	����ͼ��·��
				pRawBuffer			- ����	ͼ�񻺳���				
				iX					- ����	ͼ����
				iY					- ����	ͼ��߶�
				iChannels           - ����  ͼ��ͨ��
	��	�أ�	1-�ɹ�������-ʧ��
*******************************************************************************************/
	public native  int SaveFaceImage(String szSaveFilePath,byte[] pImgBuf,int iX,int iY,int iChannels);


/*******************************************************************************************
	��	�ܣ�	�������ͼ���ϸ��������Rect���ƾ��ο�	
	��	����	szFilePath		- ����	ԭʼͼ��·��
				iRect			- ����	Rect[0]	=x;
										Rect[1]	=y;
										Rect[2]	=width;
										Rect[3]	=height;	
				szOutFilePath	- ����	���ƾ��ο�֮���ͼ��·��
	��	�أ�	1-�ɹ�������-ʧ��
	*******************************************************************************************/
	public native  int  FaceDrawRect(String szFilePath,int[] iRect,String szOutFilePath);

	/*******************************************************************************************
	��	�ܣ�	�������ͼ���ϸ�������ĵ�������Ƶ�	
	��	����	szFilePath		- ����	ԭʼͼ��·��
				iPointPos		- ����	���������У�x1,y1,x2,y2,...��
				iPointNum       - ����  �����
				szOutFilePath	- ����	���Ƶ�֮���ͼ��·��
	��	�أ�	1-�ɹ�������-ʧ��
	*******************************************************************************************/
	public native  int FaceDrawPoint(String  szFilePath,int[]  iPointPos,int iPointNum,String szOutFilePath);

	/*******************************************************************************************
		��	�ܣ�	�������ͼ���ϸ�������ĵ�������Ƶ����
		��	����	szFilePath		- ����	ԭʼͼ��·��
					iPointPos		- ����	���������У�x1,y1,x2,y2,...��
					iPointNum       - ����  �����
					szOutFilePath	- ����	���Ƶ����֮���ͼ��·��
		��	�أ�	1-�ɹ�������-ʧ��
		*******************************************************************************************/
	public native  int FaceDrawText(String szFilePath,int[] iPointPos,int iPointNum,String szOutFilePath);
	
	/*******************************************************************************************
	��	�ܣ�	������ͼ����������Ŀ���Ƚ��а��������š�
	��	����	szFilePath		- ����	ԭʼͼ��·��
				iDstImageWidth  - ����  Ŀ��ͼ����
				szOutFilePath	- ����	Ŀ��ͼ��·��
	��	�أ�	1-�ɹ�������-ʧ��
	 *******************************************************************************************/
	public native  int ImageZoom(String szFilePath, int iDstImageWidth,String szOutFilePath);
}
