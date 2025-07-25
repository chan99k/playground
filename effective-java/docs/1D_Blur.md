이미지는 2차원 구조를 가지고 있는데, 1차원 배열로 처리하는 방식은 이미지 처리에서 매우 일반적이고 효율적인 기법입니다. 그 원리를 단계별로 설명해 드리겠습니다.

1. 2D 이미지를 1D 배열로 변환 (Flattening)

컴퓨터 메모리는 본질적으로 1차원적인 주소 공간입니다. 따라서 2차원 배열(행렬)을 메모리에 저장하려면, 이를 1차원 배열로 "펼쳐서(flatten)" 저장해야 합니다.

가장 일반적인 방법은 행 우선 순서(Row-Major Order)입니다.

* 이미지의 첫 번째 행(row)의 모든 픽셀을 순서대로 저장합니다.
* 그 바로 뒤에 두 번째 행의 모든 픽셀을 순서대로 저장합니다.
* 이 과정을 마지막 행까지 반복합니다.

BufferedImage의 getRGB() 메서드가 바로 이 역할을 수행합니다.
srcImage.getRGB(0, 0, w, h, null, 0, w)는 너비(w)와 높이(h)를 가진 이미지의 전체 픽셀 데이터를 가져와 1차원 배열 src에 행 우선 순서로 저장합니다.

2. 1D 배열에서 2D 좌표 계산하기

1차원 배열의 인덱스만 알아도, 원래 이미지의 너비(width) 값을 이용해 해당 픽셀의 2D 좌표 (x, y)를 역으로 계산할 수 있습니다.

* y = index / width (y 좌표, 즉 행 번호)
* x = index % width (x 좌표, 즉 열 번호)

예시: 4x3 (너비=4, 높이=3) 크기의 이미지가 있다고 가정해 보겠습니다.

2D 표현:

1 (0,0) (1,0) (2,0) (3,0)
2 (0,1) (1,1) (2,1) (3,1)
3 (0,2) (1,2) (2,2) (3,2)

1D 배열 표현 (총 12개 원소):

1 Index: 0 1 2 3 | 4 5 6 7 | 8 9 10 11
2 Pixel: (0,0)(1,0)(2,0)(3,0)|(0,1)(1,1)(2,1)(3,1)|(0,2)(1,2)(2,2)(3,2)

만약 index = 6인 픽셀의 원래 좌표를 찾고 싶다면:

* y = 6 / 4 = 1
* x = 6 % 4 = 2
  결과: (2, 1) -> 정확히 일치합니다.

3. ForkBlur의 연산 방식: "1D 블러"의 한계와 특징

이제 ForkBlur의 computeDirectly 메서드를 다시 살펴보겠습니다.

    1 protected void computeDirectly() {
    2     int sidePixels = (blurWidth - 1) / 2;
    3     // 이 작업이 담당하는 1D 배열의 일부를 순회
    4     for (int index = start; index < start + length; index++) {
    5         float rt = 0, gt = 0, bt = 0;
    6         // 현재 픽셀의 '주변' 픽셀을 찾음
    7         for (int mi = -sidePixels; mi <= sidePixels; mi++) {
    8             int mIndex = Math.min(Math.max(mi + index, 0), source.length - 1);
    9             int pixel = source[mIndex];

10 // ... 색상 평균 계산 ...
11 }
12 // ... 결과 저장 ...
13 }
14 }

여기서 가장 중요한 점은 다음과 같습니다.

이 ForkBlur 코드는 진정한 의미의 2D 블러를 수행하지 않습니다.

* 코드는 index를 기준으로 mi만큼 앞뒤로 떨어진 mIndex의 픽셀들을 가져옵니다. 즉, 1차원 배열상의 좌우 이웃만을 고려합니다.
* 이것은 실제 이미지의 위/아래 픽셀(예: index - width, index + width)을 전혀 고려하지 않는 수평 방향 블러(Horizontal Blur) 입니다.

이 방식의 결과:

* 한 행의 마지막 픽셀을 처리할 때, 이웃 픽셀로 다음 행의 첫 번째 픽셀을 가져오게 됩니다. (예: (3,0) 픽셀의 오른쪽 이웃으로 (0,1) 픽셀을 사용)
* 따라서 블러 효과가 이미지의 오른쪽 가장자리에서 다음 줄의 왼쪽 가장자리로 "이어지는(wrap-around)" 현상이 발생합니다.

결론

ForkBlur는 2D 이미지를 1D 배열로 변환한 뒤, 이미지의 2차원 구조(너비, 높이) 정보는 무시하고 단순히 1차원 배열상에서만 주변 픽셀을 찾아 평균을 내는 방식으로 연산을 수행합니다.

이는 Fork/Join 프레임워크의 분할/정복 개념을 설명하기 위한 단순화된 예제이기 때문입니다.

만약 정확한 2D 블러를 구현하려면, computeDirectly 메서드는 이미지의 width 값을 인자로 받아서 현재 index의 위/아래 픽셀( index - width, index + width 등)까지 정확히
계산하여 평균을 내야 합니다.

---

ap) width 값을 통해 현재 index의 위/아래 픽셀( index - width, index + width 등)까지 정확히 계산하여 평균을 내도록 코드를 수정.

동작 방식: 2D 개념을 1D 배열에서 구현


1. Flatten (2D → 1D):
  * ForkJoinMain의 blur 메서드에서 srcImage.getRGB(...)를 호출하는 순간, 2차원 이미지의 픽셀 데이터는 행 우선 순서(Row-Major Order)에 따라 1차원 배열 src로 펼쳐집니다.


2. 1D 배열 순회 (하지만 2D처럼 생각):
  * ForkBlur의 computeDirectly 메서드는 for (int index = start; index < start + length; index++) 루프를 통해 자신이 담당하는 1차원 배열의 일부를 순회합니다.


3. 좌표 복원 (1D → 2D):
  * 이것이 핵심입니다. 루프 안에서 가장 먼저 하는 일은 1차원 index와 이미지의 width를 사용해 현재 픽셀의 2D 좌표 (x, y)를 계산해내는 것입니다.


1 int y = index / width;
2 int x = index % width;

       * 이제 코드는 1차원 배열을 다루고 있지만, 논리적으로는 자신이 이미지의 (x, y) 위치에 있는 픽셀을 처리하고 있다는 것을 알게 됩니다.


4. 2D 윈도우 탐색 (수평 및 수직 블러):
  * 복원된 (x, y) 좌표를 중심으로, blurWidth x blurWidth 크기의 사각형 윈도우(이웃 픽셀 영역)를 탐색하기 위해 이중 for 루프를 사용합니다.


1         // j: 수직(Y) 방향, i: 수평(X) 방향
2         for (int j = -sidePixels; j <= sidePixels; j++) {
3             for (int i = -sidePixels; i <= sidePixels; i++) {
4                 // ...
5             }
6         }

       * 이 루프는 현재 픽셀의 위, 아래, 왼쪽, 오른쪽, 그리고 대각선 방향의 모든 이웃을 포함합니다. 이것이 바로 수평과 수직 블러를 동시에 수행하는 부분입니다.


5. 1D 인덱스로 다시 변환:
  * 탐색 중인 이웃 픽셀의 2D 좌표 (currentX, currentY)가 정해지면, 이 좌표를 다시 1차원 배열의 인덱스(mIndex)로 변환하여 source 배열에서 실제 픽셀 값을 가져옵니다.

1 int mIndex = currentY * width + currentX;
2 int pixels = source[mIndex];


비유


모눈종이에 그려진 그림을 상상해 보세요.
1. 그림을 한 줄씩 잘라서 긴 띠(1차원 배열)로 만듭니다.
2. 띠의 특정 칸(index)을 처리할 차례가 되면, 모눈종이의 한 줄에 몇 칸(width)이 들어갔는지 기억하고 있으므로, 원래 이 칸이 몇 번째 줄의 몇 번째 칸이었는지(x, y) 계산할 수 있습니다.
3. 원래 위치를 알았으니, 그 위치의 상하좌우 주변 칸들을 원래의 모눈종이에서 찾아내어 색을 섞는 것과 같습니다.

---

해당 코드 라인들은 하나의 정수(int)에 압축되어 있는 픽셀의 색상 정보를 빨강(Red), 초록(Green), 파랑(Blue)의 개별 채널 값으로 분리해내는 핵심적인 부분입니다.


이 코드가 어떻게 동작하는지 이해하려면 먼저 컴퓨터가 색상을 어떻게 정수 하나에 저장하는지 알아야 합니다.

ARGB 색상 모델: 32비트 정수 하나에 4가지 정보 담기


일반적으로 이미지의 한 픽셀 색상은 32비트(4바이트) 정수 하나로 표현됩니다. 이 32비트는 8비트씩 4개의 구역으로 나뉘어 각각 A (Alpha, 투명도), R (Red, 빨강), G (Green, 초록), B (Blue, 파랑) 정보를
담습니다.



1 32 비트 정수 (int)
2 |--------------------------------|
3 | 8비트 | 8비트 | 8비트 | 8비트 |
4 |-------|-------|-------|-------|
5 | Alpha |  Red  | Green |  Blue |
6 |--------------------------------|
7 31      24 23     16 15      8 7       0  <- 비트 위치



각 8비트는 0부터 255까지의 값을 가질 수 있습니다. pixel이라는 정수 변수에는 이 네 가지 정보가 모두 압축되어 들어있습니다.

우리가 원하는 것은 이 압축된 정보에서 R, G, B 값만 각각 따로 "추출"하는 것입니다. 이 추출 과정에 비트 연산(Bitwise Operation)이 사용됩니다.

  ---

```java
    r += (float) ((pixel & 0x00ff0000) >> 16);
    g += (float) ((pixel & 0x0000ff00) >> 8);
    b += (float) (pixel & 0x000000ff);
```


각 라인은 ① 비트 마스킹(Bit Masking)과 ② 비트 시프트(Bit Shifting), 두 단계로 동작합니다.

1. 빨강(Red) 값 추출



1 red += (float)((pixel & 0x00ff0000) >> 16);



* ① 비트 마스킹 (`& 0x00ff0000`):
  * 0x00ff0000은 16진수이며, 이진수로 표현하면 00000000 11111111 00000000 00000000 입니다.
  * & (비트 AND) 연산은 두 비트가 모두 1일 때만 1을 반환합니다.
  * 이 마스크를 pixel 값에 적용하면, Red 영역의 비트들만 그대로 남고 나머지(A, G, B)는 모두 0이 됩니다.

  예시:


1       (pixel) AAAAAAAA RRRRRRRR GGGGGGGG BBBBBBBB
2     & (mask)  00000000 11111111 00000000 00000000
3       -------------------------------------------
4     = (결과)  00000000 RRRRRRRR 00000000 00000000



* ② 비트 시프트 (`>> 16`):
  * 위 마스킹 결과에서 RRRRRRRR 부분은 여전히 16번째 비트 자리에 위치해 있어 실제 숫자 값(0~255)이 아닙니다.
  * >> 16은 비트들을 오른쪽으로 16칸 이동시키라는 의미입니다.
  * 00000000 RRRRRRRR 00000000 00000000를 오른쪽으로 16칸 밀면, RRRRRRRR 부분이 가장 오른쪽으로 이동하여 올바른 숫자 값을 갖게 됩니다.

  예시:


1     (결과)    00000000 RRRRRRRR 00000000 00000000
2     >> 16
3     = (최종)  00000000 00000000 00000000 RRRRRRRR  <- 이것이 0~255 사이의 Red 값


2. 초록(Green) 값 추출



1 green += (float)((pixel & 0x0000ff00) >> 8);



* ① 비트 마스킹 (`& 0x0000ff00`):
  * 마스크 00000000 00000000 11111111 00000000를 사용하여 Green 영역의 비트만 남깁니다.
* ② 비트 시프트 (`>> 8`):
  * 결과를 오른쪽으로 8칸 이동시켜 Green 값을 0~255 사이의 올바른 숫자로 만듭니다.

3. 파랑(Blue) 값 추출


1 blue += (float)(pixel & 0x000000ff);



* ① 비트 마스킹 (`& 0x000000ff`):
  * 마스크 00000000 00000000 00000000 11111111를 사용하여 Blue 영역의 비트만 남깁니다.
* ② 비트 시프트 (없음):
  * Blue 값은 이미 가장 오른쪽에 위치해 있으므로 비트 시프트가 필요 없습니다.

  ---

결론: +=의 의미


이렇게 추출된 R, G, B 값(0~255)들은 (float)로 형변환 된 후, red, green, blue 변수에 += (더해서 대입) 됩니다.


이 코드는 주변 픽셀들의 색상 값을 평균내어 블러(blur) 효과를 주기 위한 로직의 일부이기 때문에, 루프를 돌면서 이웃 픽셀들의 R, G, B 값을 각각 계속 더해 나가는 것입니다. 루프가 끝나면 더해진 총합을
픽셀 개수로 나누어 평균을 구하게 됩니다.

---

수평/수직 분리 블러 방식이 더 효율적인 이유는 픽셀당 총 연산 횟수를 극적으로 줄여주기 때문입니다.


이를 분리 가능한 필터(Separable Filter)의 원리라고 부릅니다.

핵심 원리: 2차원 연산을 1차원 연산 두 번으로 분리


간단한 비유로 시작하겠습니다.
15x15 크기의 정사각형을 색칠해야 한다고 상상해 보세요.


* 비효율적인 방법 (동시 2D 블러): 아주 작은 붓으로 15x15=225개의 모든 칸을 하나하나 꼼꼼히 칠하는 것과 같습니다.
* 효율적인 방법 (분리 블러): 넓은 붓으로 가로로 15번 쓱쓱 칠한 다음, 다시 세로로 15번 쓱쓱 칠하는 것과 같습니다. 훨씬 빠르겠죠.


이제 이것을 실제 연산 횟수로 비교해 보겠습니다. blurWidth가 15라고 가정합니다.

  ---

1. 비효율적인 방법: 수평/수직 동시 블러 (compute2DBlur)


하나의 결과 픽셀을 계산하기 위해 15x15 크기의 정사각형 윈도우 안에 있는 모든 픽셀을 참조해야 합니다.


* 참조하는 픽셀 수: 15 * 15 = 225개
* 픽셀당 연산량 (대략):
  * 225번의 픽셀 값 읽기
  * 225번의 덧셈 (R, G, B 각 채널별로)
  * 1번의 나눗셈 (평균 계산)


결과적으로, 하나의 픽셀을 계산하는 데 약 225회에 비례하는 연산이 필요합니다.
이를 연산 복잡도로 표현하면 O(N²) 입니다. (여기서 N은 blurWidth)



  ---

2. 효율적인 방법: 수평/수직 분리 블러

이 방식은 두 단계로 나뉩니다.


1단계: 수평 블러 (`computeHorizontalBlur`)
* 하나의 임시 픽셀 값을 계산하기 위해 수평 방향으로만 픽셀을 참조합니다.
* 참조하는 픽셀 수: 15개
* 픽셀당 연산량 (대략): 15번의 덧셈과 1번의 나눗셈
* 연산 복잡도는 O(N) 입니다.


2단계: 수직 블러 (`computeVerticalBlur`)
* 1단계에서 계산된 임시 결과값을 입력으로 받습니다.
* 하나의 최종 픽셀 값을 계산하기 위해 수직 방향으로만 픽셀을 참조합니다.
* 참조하는 픽셀 수: 15개
* 픽셀당 연산량 (대략): 15번의 덧셈과 1번의 나눗셈
* 연산 복잡도는 O(N) 입니다.


!Separable Blur (https.i.imgur.com/sFv1bQk.png)


두 단계를 합친 총 연산량은 하나의 픽셀당 약 `15 + 15 = 30`회에 비례하는 연산이 됩니다.
총 연산 복잡도는 O(N) + O(N) = O(2N) 입니다.

  ---

결론: 왜 압도적으로 효율적인가?



┌───────────┬─────────┬──────────────────────────────┐
│ 방식        │ 연산 복잡도  │ `blurWidth = 15`일 때 연산량 (비례) │
├───────────┼─────────┼──────────────────────────────┤
│ 동시 2D 블러  │ `O(N²)` │ `15 * 15 = 225`              │
│ **분리 블러** │ `O(2N)` │ `15 + 15 = 30`               │
└───────────┴─────────┴──────────────────────────────┘

`225`회 vs `30`회.


수치에서 볼 수 있듯이, 분리 블러 방식은 동시 처리 방식에 비해 약 7.5배 더 효율적입니다. blurWidth가 25처럼 더 커진다면 그 차이는 625 vs 50으로, 12배 이상으로 벌어집니다. 블러 반경이 커질수록 효율성
차이는 기하급수적으로 증가합니다.


추가적인 이점: 캐시 효율성 (Cache-Friendliness)


* 수평 블러는 메모리상에 연속적으로 나열된 픽셀들을 순서대로 접근합니다. 이는 CPU 캐시가 매우 효율적으로 동작하게 만들어 실제 성능을 더욱 향상시킵니다.
* 반면 수직 블러나 동시 2D 블러는 메모리상에서 width만큼 떨어진 위치로 계속 점프(stride)해야 하므로 캐시 효율이 떨어집니다.


이러한 이유로, 동일한 시각적 효과를 내는 대부분의 2D 이미지 필터(블러, 샤프닝 등)는 실제 구현 시 연산량을 줄이기 위해 이와 같은 분리 가능한(separable) 방식으로 구현됩니다.
