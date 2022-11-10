import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class ImageMethods {

    public static BufferedImage mirrorImage(BufferedImage inputImage) {
        // Get source image dimension
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // BufferedImage for mirror image
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Create mirror image pixel by pixel
        for (int y = 0; y < height; y++) {
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {

                int p = inputImage.getRGB(lx, y);

                outputImage.setRGB(rx, y, p);
            }
        }
        return outputImage;
    }

    public static BufferedImage arrayToImage(int sideLength, int[] greyscaleArr) {
        int[] rgbArr = new int[greyscaleArr.length];
        for (int i = 0; i < greyscaleArr.length; i++) {
            rgbArr[i] = new Color(greyscaleArr[i], greyscaleArr[i], greyscaleArr[i]).getRGB();
        }


        BufferedImage img = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_INT_RGB);
//        img.setRGB(0, 0, img.getWidth(), img.getHeight(), rgbArr, 0, 10);
        int x = 0;
        int y = 0;
        for (int i = 0; i < rgbArr.length; i++, x++) {
            if (x >= sideLength) {
                x = 0;
                y += 1;
            }
            img.setRGB(y, x, rgbArr[i]);
        }
        display(img);
        return img;
    }

    public static BufferedImage arrayToImageRGB(int sideLength, int[] rgbArr) {


        BufferedImage img = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_INT_RGB);
        int index = 0;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                img.setRGB(x, y, new Color(rgbArr[index], rgbArr[index + 1], rgbArr[index + 2]).getRGB());

                index += 3;
            }
        }
        return img;
    }

    public static BufferedImage arrayToImageRGB(double[][][] rgbArr, int width, int height) {

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                double[] pixel = rgbArr[x][y];
                Arr.limitOverwrite(pixel,0,1);
                img.setRGB(x, y, new Color(
                        (int) (pixel[0] * 255),
                        (int) (pixel[1] * 255),
                        (int) (pixel[2] * 255)
                ).getRGB());
            }
        }

        return img;
    }

    public static BufferedImage arrayToImage(double[] greyscaleArr, int width, int height) {


        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        img.setRGB(0, 0, img.getWidth(), img.getHeight(), greyscaleArr, 0, 10);
        int index = 0;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                img.setRGB(x, y, new Color(
                        (int) (greyscaleArr[index] * 255),
                        (int) (greyscaleArr[index] * 255),
                        (int) (greyscaleArr[index] * 255)
                ).getRGB());

                index++;
            }
        }
        return img;
    }

    public static BufferedImage cropImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int startingX = (originalImage.getWidth() - targetWidth) / 2;
        int startingY = (originalImage.getHeight() - targetHeight) / 2;
        return originalImage.getSubimage(startingX, startingY, targetWidth, targetHeight);
    }

    public static BufferedImage cropImage(BufferedImage originalImage, int targetWidth, int targetHeight, double posX, double posY) {
        BufferedImage newImage = new BufferedImage(
                originalImage.getWidth() * 2,
                originalImage.getHeight() * 2,
                originalImage.getType()
        );
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(originalImage, originalImage.getWidth() / 2, originalImage.getHeight() / 2, null);

        int startingX = (int) (originalImage.getWidth() * (0.5 + posX) - targetWidth / 2);
        int startingY = (int) (originalImage.getHeight() * (0.5 + posY) - targetHeight / 2);
        return newImage.getSubimage(startingX, startingY, targetWidth, targetHeight);
    }

    public static BufferedImage toBlackAndWhite(BufferedImage image) {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphic = result.createGraphics();
        graphic.drawImage(image, 0, 0, Color.WHITE, null);
        graphic.dispose();
        return result;
    }

    public static BufferedImage resize(BufferedImage originalImage, int targetWidth, int targetHeight) {
        return ImageMethods.toBufferedImage(originalImage.getScaledInstance(
                targetWidth,
                targetHeight,
                Image.SCALE_SMOOTH
        ));
    }

    public static BufferedImage resize(BufferedImage originalImage, int widthAndHeight) {

        return resize(Image.SCALE_SMOOTH, originalImage, widthAndHeight);
    }

    public static BufferedImage resize(int scalingAlgorithm, BufferedImage originalImage, int widthAndHeight) {

        if (originalImage.getWidth() == widthAndHeight && originalImage.getHeight() == widthAndHeight) {
            return originalImage;
        } else {

            double aspectRatio = (double) originalImage.getWidth() / (double) originalImage.getHeight();
            int targetWidth;
            int targetHeight;
            if (originalImage.getWidth() > originalImage.getHeight()) {
                targetWidth = (int) (aspectRatio * widthAndHeight);
                targetHeight = widthAndHeight;
            } else {
                targetWidth = widthAndHeight;
                targetHeight = (int) (widthAndHeight / aspectRatio);

            }
            return ImageMethods.toBufferedImage(originalImage.getScaledInstance(
                    targetWidth,
                    targetHeight,
                    scalingAlgorithm
            ));
        }
    }


    public static double[] imageToArray(String imagePath, int widthAndHeight) throws IOException {
        File file = new File(imagePath);
        BufferedImage img = ImageIO.read(file);


        img = resize(img, widthAndHeight);
        img = cropImage(img, widthAndHeight, widthAndHeight);
//        display(img);
        return getImageData(img);
    }

    public static double[] imageToArray(BufferedImage img, int widthAndHeight) {
        img = resize(img, widthAndHeight);
        img = cropImage(img, widthAndHeight, widthAndHeight);
//        display(img);
        return getImageData(img);
    }

    public static double[][][] toArrayRGB(BufferedImage img, int widthAndHeight) {
        img = resize(img, widthAndHeight);
        img = cropImage(img, widthAndHeight, widthAndHeight);
//        display(img);
        return toArrayRGB(img);
    }

    public static double[][][] toArrayRGB(BufferedImage img, int width, int height) {
        img = resize(img, width, height);
        img = cropImage(img, width, height);
//        display(img);
        return toArrayRGB(img);
    }

    public static double[] imageToOutputArray(String imagePath, int widthAndHeight) throws IOException {
        File file = new File(imagePath);
        BufferedImage img = ImageIO.read(file);

//        display(img);
        img = resize(img, widthAndHeight);
//        display(img);
        img = cropImage(img, widthAndHeight, widthAndHeight);
        double[] imageData = getImageData(img);
        for (int i = 0; i < imageData.length; i++) {
            imageData[i] = imageData[i] * 2 - 1;
        }
        return imageData;
    }


    public static void display(BufferedImage img) {
        JFrame jFrame = new JFrame();

        jFrame.setLayout(new FlowLayout());

        JLabel jLabel = new JLabel();
        final BufferedImage[] scaledImage = {resize(img, 300)};
        jFrame.setSize(scaledImage[0].getWidth() + 20, scaledImage[0].getHeight() + 50);
        final ImageIcon[] imageIcon = {new ImageIcon(scaledImage[0])};
        jLabel.setIcon(imageIcon[0]);

        jFrame.add(jLabel);
        jFrame.setVisible(true);

        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

                scaledImage[0] = resize(
                        img,
                        (int) Arr.min(new double[]{jFrame.getHeight() - 80, jFrame.getWidth() - 20})
                );
                imageIcon[0] = new ImageIcon(scaledImage[0]);
                jLabel.setIcon(imageIcon[0]);

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
//        jFrame.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (Arrays.equals(sizeBefore[0], new int[]{jFrame.getWidth(), jFrame.getHeight()})) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
//
//                scaledImage[0] = resize(
//                        img,
//                        (int) Arr.min(new double[]{jFrame.getHeight() - 50, jFrame.getWidth() - 20})
//                );
//                imageIcon[0] = new ImageIcon(scaledImage[0]);
//                jLabel.setIcon(imageIcon[0]);
//                sizeBefore[0] = new int[]{jFrame.getWidth(), jFrame.getHeight()};
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException ex) {
//                    throw new RuntimeException(ex);
//                }
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//        });
    }

    public static void display(BufferedImage img, int size) {
        BufferedImage scaledImage = resize(img, size);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        JFrame jFrame = new JFrame();

        jFrame.setLayout(new FlowLayout());

        jFrame.setSize(scaledImage.getWidth() + 20, scaledImage.getHeight() + 50);
        JLabel jLabel = new JLabel();

        jLabel.setIcon(imageIcon);
        jFrame.add(jLabel);
        jFrame.setVisible(true);

        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public static double[] getImageData(BufferedImage img) {
        double[] greyscaleArray = new double[img.getWidth() * img.getHeight()];

        int index = 0;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int pixel = img.getRGB(x, y);
                Color color = new Color(pixel, false);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                greyscaleArray[index] = Arr.average(red, green, blue) / 255.0;
                index++;
            }
        }
        return greyscaleArray;
    }

    public static double[][][] toArrayRGB(BufferedImage img) {
        double[][][] rgbArray = new double[img.getWidth()][img.getHeight()][3];

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int pixel = img.getRGB(x, y);
                Color color = new Color(pixel, false);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                rgbArray[x][y] = new double[]{red / 255.0, green / 255.0, blue / 255.0};
            }
        }
        return rgbArray;
    }

    public static BufferedImage screenshot(double cropByThisMuch, double posX, double posY) throws AWTException {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRect = new Rectangle(dimension);
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        capture = cropImage(
                capture,
                (int) (screenRect.height / cropByThisMuch),
                (int) (screenRect.height / cropByThisMuch),
                posX,
                posY
        );
        return capture;
    }

    public static BufferedImage loadImage(String imagePath) {
        File file = new File(imagePath);
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveImage(BufferedImage img, String imagePathAndName) {
//        System.out.println(imagePathAndName);
        if (!imagePathAndName.endsWith(".png")) {
            imagePathAndName += ".png";
        }
        File outputFile = new File(imagePathAndName);
        try {
            ImageIO.write(img, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        // Draw the image on to the buffered image
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bufferedImage;
    }

    public static BufferedImage cropToSquare(BufferedImage img) {
        int width = (int) Arr.min(img.getWidth(), img.getHeight());
        return cropImage(img, width, width);
    }


    public static BufferedImage combineImages(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            try {
                throw new Exception("images don't match");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        BufferedImage combined = new BufferedImage(img1.getWidth() * 2, img1.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = combined.createGraphics();
        g2d.drawImage(img1, 0, 0, null);
        g2d.drawImage(img2, img1.getWidth(), 0, null);
        return combined;
    }

}
