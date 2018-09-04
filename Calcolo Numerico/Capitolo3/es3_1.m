A = [1 0 0; 3 1 0; 4 1 2];
b = [1 2 3];
x = sistema_triang_inf(A, b);

function [b] = sistema_triang_inf(A, b)
    for j = 1 : length(A)
        b(j) = b(j)/A(j,j);
        for i = j+1 : length(A)
            b(i) = b(i)-A(i,j)*b(j);
        end
    end
end
